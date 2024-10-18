package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.model.DTO.orderDTO;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.service.snappingUpOrderService;
import org.cwy.cloud.utils.AuthTokenUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SnapingApi")
public class SnappingUpOrderController {
    @Resource
    private snappingUpOrderService snappingUpOrderService;


    @GetMapping("/GetSnapingOrderId")
    public CommonResult GetSnapingOrderId(@RequestParam("goodsId") Integer goodsId,
                                          @RequestParam("SnapingId") Integer SnapingId,
                                          @RequestParam("sku") Integer sku,
                                          @RequestParam("buyNub") Integer buyNub){
        Integer userId = AuthTokenUtil.getUserId();;
        Integer id = snappingUpOrderService.getSnappingOrderId(userId, goodsId,SnapingId,sku,buyNub);
        return CommonResult.success(id);
    }

    @PostMapping(value = "/addOrder")
    public CommonResult addOrder(@RequestBody orderDTO orderData) throws InterruptedException {
        Integer userId = AuthTokenUtil.getUserId();;
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
