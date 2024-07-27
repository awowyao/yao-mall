package org.cwy.cloud.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.DTO.orderDTO;
import org.cwy.cloud.PO.orderMsgPO;
import org.cwy.cloud.PO.orderPO;
import org.cwy.cloud.feign.goodsFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.feign.userFeign;
import org.cwy.cloud.mapper.orderMapper;
import org.cwy.cloud.mapper.orderMsgMapper;
import org.cwy.cloud.model.orderSetting;
import org.cwy.cloud.service.orderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class orderServiceImp implements orderService {
    @Resource
    private orderMapper orderMapper;
    @Resource
    private orderMsgMapper orderMsgMapper;
    @Resource
    private uniqidFeign uniqidFeign;
    @Resource
    private userFeign userFeign;
    @Resource
    private goodsFeign goodsFeign;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Override
    public Integer getOrderId(Integer userId, Integer goodsId, Integer buyNub) {
        orderPO order = new orderPO();
        order.setBuyNub(buyNub);
        order.setBuyGoodsId(goodsId);
        order.setOid(uniqidFeign.GetId(1));
        order.setState(0);
        order.setBuy_user_id(userId);
        orderMapper.insert(order);
        return order.getOid();
    }


    @Override
    @GlobalTransactional(name = "addOrder", rollbackFor = Exception.class)
    public Integer addOrder(orderDTO orderData) throws InterruptedException {
        LambdaQueryWrapper<orderPO> query = new LambdaQueryWrapper<>();
        query.eq(orderPO::getOid, orderData.getOid());
        orderPO order = orderMapper.selectOne(query);
        if (order.getState()!=0){
            return 0;
        }
        order.setState(1);
        orderMsgPO newMsg = new orderMsgPO();
        Integer omid =  uniqidFeign.GetId(1);
        newMsg.setOId(orderData.getOid());
        newMsg.setOmId(omid);
        order.setOrderMsgid(omid);
        Integer goodsId = orderData.getGoodsId();
        Map<String,Object> goodsData = (Map<String,Object>)goodsFeign.getGoodsById(goodsId).getData();
        if (goodsFeign.subtractInventory((Integer) goodsData.get("id"), order.getBuyNub())==1){
            Double price = (Double) goodsData.get("price") * order.getBuyNub();
            newMsg.setPrice(price);
            newMsg.setBuyersId(orderData.getBuyUserId());
            newMsg.setStoreId((Integer) goodsData.get("store"));
            newMsg.setGoodsId((Integer) goodsData.get("id"));
            newMsg.setGoodsTitle((String) goodsData.get("title"));
            newMsg.setCouponsPrice(price);
            if (orderData.getCouponsId()!=null) {
                Map<String,Object> couponData = (Map<String,Object>)goodsFeign.getCouponsById(orderData.getCouponsId()).getData();
                CouponsDTO Coupons = new CouponsDTO(orderData.getCouponsId(), orderData.getBuyUserId());
                if (price >=((Integer) couponData.get("max")) && userFeign.useCoupons(Coupons)==1){
                    newMsg.setCouponsPrice(price- (Integer) couponData.get("subtract"));
//                    TimeUnit.SECONDS.sleep(20);

                    rabbitTemplate.convertAndSend("user.order.delay_exchange", "user.order.delay_key", newMsg, message -> {
                        message.getMessageProperties().setExpiration(orderSetting.ORDERTIME);
                        return message;
                    });
//                    throw new ArithmeticException();
                }
                else
                    return 3;
            }

            orderMapper.update(order, query);
            return orderMsgMapper.insert(newMsg);
        }else {
            return 2;
        }
    }


}
