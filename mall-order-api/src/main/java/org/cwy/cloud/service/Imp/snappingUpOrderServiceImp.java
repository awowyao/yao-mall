package org.cwy.cloud.service.Imp;

import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.cwy.cloud.model.DTO.orderDTO;
import org.cwy.cloud.model.PO.orderMsgPO;
import org.cwy.cloud.model.PO.orderPO;
import org.cwy.cloud.feign.goodsFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.service.snappingUpOrderService;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class snappingUpOrderServiceImp implements snappingUpOrderService {
    @Resource
    private uniqidFeign uniqidFeign;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private goodsFeign goodsFeign;

    @Override
    public Integer getSnappingOrderId(Integer userId, Integer goodsId, Integer buyNub) {
        orderPO order = new orderPO();
        order.setBuyNub(buyNub);
        order.setBuyGoodsId(goodsId);
        Integer orderId = uniqidFeign.GetId(1);
        order.setOid(orderId);
        order.setState(0);
        order.setBuy_user_id(userId);
        BoundHashOperations<String, Object, Object> orderHash = redisTemplate.boundHashOps(userId+"_"+orderId);
        BoundHashOperations<String, Object, Object> orderDataHash = redisTemplate.boundHashOps("snappingUpData_"+goodsId);
        if ((Integer)orderDataHash.get("stock_limit")<buyNub){
            return 0;
        }
        orderHash.put("buyNub", buyNub);
        orderHash.put("buyGoodsId", goodsId);
        orderHash.put("state", 0);
        orderHash.put("buy_user_id", userId);
        orderHash.expire(30, TimeUnit.MINUTES);
//        orderMapper.insert(order);
//        redisTemplate.opsForValue().set();
        return order.getOid();
    }

    @Override
    public Integer addSnappingUpOrder(orderDTO orderData) {

        if (!redisTemplate.hasKey(orderData.getBuyUserId()+"_"+orderData.getOid())){
            return 0;
        }
        BoundHashOperations<String, Object, Object> orderHash = redisTemplate.boundHashOps(orderData.getBuyUserId()+"_"+orderData.getOid());
        orderPO order = BeanUtil.mapToBean(orderHash.entries(), orderPO.class, true);
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
        BoundValueOperations<String, Object> goodsStock= redisTemplate.boundValueOps("snappingUpStock_" + order.getBuyGoodsId());
//        Map<String,Object> goodsData = (Map<String,Object>) goodsFeign.getGoodsById(goodsId).getData();
        BoundHashOperations<String, Object, Object> goodsData = redisTemplate.boundHashOps("snappingUpData_" + order.getBuyGoodsId());
//        order.getBuyNub()
        if (goodsStock.decrement(15)>=0){
            Double price = (Double) goodsData.get("price") * order.getBuyNub();
            newMsg.setPrice(price);
            newMsg.setBuyersId(orderData.getBuyUserId());
            newMsg.setStoreId((Integer) goodsData.get("store"));
            newMsg.setGoodsId((Integer) goodsData.get("id"));
            newMsg.setGoodsTitle((String) goodsData.get("title"));
            newMsg.setCouponsPrice(price);
            newMsg.setCreateTime(LocalDateTime.now());
            newMsg.setUpdateTime(LocalDateTime.now());

            orderHash.put("state", order.getState());
            orderHash.put("orderMsgid", order.getOrderMsgid());
            Map<String, Object> orderMsgMap = BeanUtil.beanToMap(newMsg);
            BoundHashOperations<String, Object, Object> orderMsg = redisTemplate.boundHashOps("orderMsg_" + newMsg.getOmId());
            orderMsg.putAll(orderMsgMap);
            System.out.println(orderMsgMap);
            return 1;
//            if (orderData.getCouponsId()!=null) {
//                Map<String,Object> couponData = (Map<String,Object>)goodsFeign.getCouponsById(orderData.getCouponsId()).getData();
//                CouponsDTO Coupons = new CouponsDTO(orderData.getCouponsId(), orderData.getBuyUserId());
//                if (price >=((Integer) couponData.get("max")) && userFeign.useCoupons(Coupons)==1){
//                    newMsg.setCouponsPrice(price- (Integer) couponData.get("subtract"));
////                    TimeUnit.SECONDS.sleep(20);
//                        rabbitTemplate.convertAndSend("user.order.delay_exchange", "user.order.delay_key", newMsg, message -> {
//                        message.getMessageProperties().setExpiration(orderSetting.ORDERTIME);
//                        return message;
//                    });
////                    throw new ArithmeticException();
//                }
//                else
//                    return 3;
//            }
        }

        return 0;
    }
}
