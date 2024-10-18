package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.model.DTO.orderDTO;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.orderPage;
import org.cwy.cloud.model.PO.orderMsgPO;
import org.cwy.cloud.service.orderService;
import org.cwy.cloud.utils.AuthTokenUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        Integer userId = AuthTokenUtil.getUserId();;
        Integer id = orderservice.getOrderId(userId, goodsId, buyNub);
        return CommonResult.success(id);
    }
    @GetMapping("/order")
    public CommonResult GetOrderList(orderPage page){
        Integer userId = AuthTokenUtil.getUserId();
        page.setUserId(userId);
        Map<String, Object> data = orderservice.getOrderList(page);
        return CommonResult.success(data);
    }

    @PostMapping("/addOrder")
    public CommonResult addOrder(@RequestBody orderDTO orderData) throws InterruptedException {
        Integer userId = AuthTokenUtil.getUserId();;
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
