package org.cwy.cloud.service.Imp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.common.orderSetting;
import org.cwy.cloud.feign.snappingUpFeign;
import org.cwy.cloud.feign.userFeign;
import org.cwy.cloud.model.DTO.orderDTO;
import org.cwy.cloud.model.PO.orderMsgPO;
import org.cwy.cloud.model.PO.orderPO;
import org.cwy.cloud.feign.goodsFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.model.PO.snappingUpPO;
import org.cwy.cloud.service.snappingUpOrderService;
import org.cwy.cloud.util.RedisUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import static org.cwy.cloud.model.rabbitMqQueueKey.orderUpMysqlExchange;
import static org.cwy.cloud.model.rabbitMqQueueKey.orderUpMysqlKey;
import static org.cwy.cloud.model.snappingUpStatic.*;

@Service
public class snappingUpOrderServiceImp implements snappingUpOrderService {
    @Resource
    private uniqidFeign uniqidFeign;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private goodsFeign goodsFeign;
    @Resource
    private userFeign userFeign;
    @Resource
    private snappingUpFeign snappingUpFeign;

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Override
    public Integer getSnappingOrderId(Integer userId, Integer goodsId, Integer snapingId, Integer sku, Integer buyNub) {
        orderPO order = new orderPO();
        order.setBuyNub(buyNub);
        order.setBuyGoodsId(goodsId);
        Integer orderId = uniqidFeign.GetId(1);
        order.setOid(orderId);
        order.setState(0);
        order.setSkuId(sku);
        order.setBuyUserId(userId);
        redisUtil.setBean(SnappingOrderKey+userId+"_"+orderId, order,30, TimeUnit.SECONDS);
//        BoundHashOperations<String, Object, Object> orderHash = redisTemplate.boundHashOps(userId+"_"+orderId);
        snappingUpPO snappingUpData = redisUtil.ToBean(RedisSnappingUpKey+goodsId, snappingUpPO.class);
        if (snappingUpData.getStockLimit()<buyNub){
            return 0;
        }
//        rabbitTemplate.convertAndSend(orderUpMysqlExchange, orderUpMysqlKey, order, message -> {
//            message.getMessageProperties();
//            return message;
//        });

//        orderMapper.insert(order);
//        redisTemplate.opsForValue().set();
        return order.getOid();
    }

    @Override
    public Integer addSnappingUpOrder(orderDTO orderData) {
        if (!redisUtil.hasKey(SnappingOrderKey+orderData.getBuyUserId()+"_"+orderData.getOid())){
            return 0;
        }
//        BoundHashOperations<String, Object, Object> orderHash = redisTemplate.boundHashOps(orderData.getBuyUserId()+"_"+orderData.getOid());
        orderPO order = redisUtil.ToBean(SnappingOrderKey+orderData.getBuyUserId()+"_"+orderData.getOid(), orderPO.class);
//        System.out.println(order);
//        orderPO order = BeanUtil.mapToBean(orderHash.entries(), orderPO.class, true);
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
//        Map<String,Object> goodsData = (Map<String,Object>) goodsFeign.getGoodsById(goodsId).getData();
        snappingUpPO goodsData = redisUtil.ToBean(RedisSnappingUpKey + order.getBuyGoodsId(), snappingUpPO.class);
//        order.getBuyNub()
        synchronized (order.getBuyGoodsId()){
            if (Integer.parseInt(redisUtil.get(snappingUpStockKey + order.getBuyGoodsId())) >= order.getBuyGoodsId()){
//            Double price = goodsData.getPrice() * order.getBuyNub();
                Double price = snappingUpFeign.getSkuPrice(order.getSkuId()) * order.getBuyNub();

                newMsg.setPrice(price);
                newMsg.setBuyersId(orderData.getBuyUserId());
                newMsg.setStoreId(666);
                newMsg.setGoodsId(goodsData.getGid());
                newMsg.setGoodsTitle("123456");
                newMsg.setCouponsPrice(price);
                newMsg.setCreateTime(LocalDateTime.now());
                newMsg.setUpdateTime(LocalDateTime.now());

//            Map<String, Object> orderMsgMap = BeanUtil.beanToMap(newMsg);
//            BoundHashOperations<String, Object, Object> orderMsg = redisTemplate.boundHashOps("orderMsg_" + newMsg.getOmId());
//            orderMsg.putAll(orderMsgMap);

                if (orderData.getCouponsId()!=null) {
                    Map<String,Object> couponData = (Map<String,Object>)goodsFeign.getCouponsById(orderData.getCouponsId()).getData();
                    CouponsDTO Coupons = new CouponsDTO(orderData.getCouponsId(), orderData.getBuyUserId());
                    if (price >=((Integer) couponData.get("max")) && userFeign.useCoupons(Coupons)==1){
                        newMsg.setCouponsPrice(price- (Integer) couponData.get("subtract"));
//
//                    TimeUnit.SECONDS.sleep(20);
//                    throw new ArithmeticException();
                    }
                    else
                        return 3;
                }
                redisUtil.setBean(orderMsgKey + newMsg.getOmId(), newMsg, 30, TimeUnit.SECONDS);
                redisUtil.setBean(SnappingOrderKey+orderData.getBuyUserId()+"_"+orderData.getOid(), order,30, TimeUnit.SECONDS);
                redisUtil.decrement(snappingUpStockKey + order.getBuyGoodsId(), order.getBuyGoodsId());
                return 1;
            }else {
                return 2;
            }
        }



    }


}
