package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.apache.ibatis.jdbc.Null;
import org.cwy.cloud.DTO.orderDTO;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.service.orderService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderApi")
public class orderController {
    @Resource
    private orderService orderservice;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/GetOrderId")
    public CommonResult GetOrderId(@RequestParam("goodsId") Integer goodsId,
                                   @RequestParam("buyNub") Integer buyNub){
        Integer userId = 3;
        Integer id = orderservice.getOrderId(userId, goodsId, buyNub);
        return CommonResult.success(id);
    }


    @PostMapping("/addOrder")
    public CommonResult addOrder(@RequestBody orderDTO orderData) throws InterruptedException {
        Integer userId = 3;
        orderData.setBuyUserId(userId);
        Integer id = orderservice.addOrder(orderData);
        if (id==1){
            return CommonResult.success(id);
        }else if (id==2){
            return CommonResult.failed("库存不足");
        }else if (id==3){
            return CommonResult.failed("优惠卷不可用");
        }else {
            return CommonResult.failed();
        }
    }

}
