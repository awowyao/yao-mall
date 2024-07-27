package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.model.DTO.orderDTO;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.service.snappingUpOrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SnapingApi")
public class SnappingUpOrderController {
    @Resource
    private snappingUpOrderService snappingUpOrderService;

    @GetMapping("/test")
    public String test() {
        return "123";
    }
    @GetMapping("/GetSnapingOrderId")
    public CommonResult GetSnapingOrderId(@RequestParam("goodsId") Integer goodsId,
                                          @RequestParam("buyNub") Integer buyNub){
        Integer userId = 3;
//        Integer id = snappingUpOrderService.getSnappingOrderId(userId, goodsId, buyNub);
        return CommonResult.success(1);
    }

    @PostMapping("/addOrder")
    public CommonResult addOrder(@RequestBody orderDTO orderData) throws InterruptedException {
        Integer userId = 3;
        orderData.setBuyUserId(userId);
        Integer id = snappingUpOrderService.addSnappingUpOrder(orderData);
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
