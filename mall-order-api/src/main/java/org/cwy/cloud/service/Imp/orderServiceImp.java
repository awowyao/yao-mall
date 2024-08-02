package org.cwy.cloud.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import org.apache.ibatis.jdbc.Null;
import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.common.orderStatueCode;
import org.cwy.cloud.model.DTO.afterSalesDTO;
import org.cwy.cloud.model.DTO.orderDTO;
import org.cwy.cloud.model.DTO.orderMsgDTO;
import org.cwy.cloud.model.DTO.orderPage;
import org.cwy.cloud.model.PO.orderMsgPO;
import org.cwy.cloud.model.PO.orderPO;
import org.cwy.cloud.feign.goodsFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.feign.userFeign;
import org.cwy.cloud.mapper.orderMapper;
import org.cwy.cloud.mapper.orderMsgMapper;
import org.cwy.cloud.common.orderSetting;
import org.cwy.cloud.result.MyAssert;
import org.cwy.cloud.service.orderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
        order.setBuyUserId(userId);
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
        newMsg.setAddressId(orderData.getAddressId());
        newMsg.setOrderStatue(2);
        order.setOrderMsgid(omid);
        Integer goodsId = order.getBuyGoodsId();
        Map<String,Object> goodsData = (Map<String, Object>) goodsFeign.getGoodsByIds(goodsId).getData();
        goodsData = (Map<String, Object>) goodsData.get("data");
        if (goodsFeign.subtractInventory(goodsId, order.getBuyNub())==1){
            Double price = (Double) goodsData.get("price") * order.getBuyNub();
            newMsg.setPrice(price);
            newMsg.setBuyersId(orderData.getBuyUserId());
            newMsg.setStoreId((Integer) goodsData.get("store"));
            newMsg.setGoodsId((Integer) goodsData.get("id"));
            newMsg.setGoodsTitle((String) goodsData.get("title"));
            newMsg.setCouponsPrice(price);
            if (orderData.getCouponsId()!=null) {
                Map<String,Object> couponData = goodsFeign.checkCouponsById(goodsId ,orderData.getCouponsId());
                MyAssert.notEmpty(couponData, "优惠卷不存在或不可用");
                CouponsDTO Coupons = new CouponsDTO(orderData.getCouponsId(), order.getBuyUserId());
                if (price >=((Integer) couponData.get("max")) && userFeign.useCoupons(Coupons)==1){
                    newMsg.setCouponsPrice(price- (Integer) couponData.get("subtract"));
//                    TimeUnit.SECONDS.sleep(20);
                    newMsg.setCouponsId((Integer) couponData.get("id"));
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

    @Override
    public List<orderMsgPO> getOrder(orderPage page) {
        LambdaQueryWrapper<orderMsgPO> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Page<orderMsgPO> orderPage = new Page<>(page.getPage(), page.getPageSize());
        IPage<orderMsgPO> orderIPage = orderMsgMapper.selectPage(orderPage , orderLambdaQueryWrapper);
        MyAssert.notEmpty(orderIPage.getRecords(), "查询为空");
        return orderIPage.getRecords();
    }

    @Override
    public void deleteOrder(Integer id) {
        LambdaUpdateWrapper<orderMsgPO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(orderMsgPO::getOrderStatue, orderStatueCode.END);
        lambdaUpdateWrapper.eq(orderMsgPO::getOmId, id);
        MyAssert.isZero(orderMsgMapper.update(lambdaUpdateWrapper));
    }

    @Override
    public void editOrder(orderMsgDTO orderMsg) {
        orderMsgPO order = new orderMsgPO();
        BeanUtils.copyProperties(orderMsg, order);
        MyAssert.isZero(orderMsgMapper.updateById(order));
    }

    @Override
    public void afterSales(afterSalesDTO afterSales) {
        if (afterSales.isDisposal()) {
            LambdaUpdateWrapper<orderMsgPO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(orderMsgPO::getOmId, afterSales.getOmId());
            if (afterSales.getFilingType() == orderStatueCode.FILED_REFUND) {
                lambdaUpdateWrapper.set(orderMsgPO::getOrderStatue, orderStatueCode.REFUND);
            } else if (afterSales.getFilingType() == orderStatueCode.FILED_RETURNABLE) {
                lambdaUpdateWrapper.set(orderMsgPO::getOrderStatue, orderStatueCode.RETURNABLE);
            }
            MyAssert.isZero(orderMsgMapper.update(lambdaUpdateWrapper));
        }else {
//            System.out.println(123);
            throw new IllegalArgumentException("传入类型错误");
//            MyAssert.notNull(null, "传入类型错误");
        }
    }

    @Override
    public Map<String, Object> getOrderList(orderPage page) {
        LambdaQueryWrapper<orderMsgPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(orderMsgPO::getBuyersId, page.getUserId());
        Page<orderMsgPO> orderPage = new Page<>(page.getPage(), page.getPageSize());
        IPage<orderMsgPO> orderMsgPOIPage = orderMsgMapper.selectPage(orderPage, lambdaQueryWrapper);
        Map<String, Object> orderListMap = new HashMap<>();
        MyAssert.notEmpty(orderMsgPOIPage.getRecords(), "查询结果为0");
        orderListMap.put("total", orderMsgPOIPage.getTotal());
        orderListMap.put("pages", orderMsgPOIPage.getPages());
        orderListMap.put("data", orderMsgPOIPage.getRecords());
        return orderListMap;
    }


}
