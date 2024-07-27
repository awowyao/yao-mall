package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.PO.couponsPO;
import org.cwy.cloud.service.goodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couponsApi")
public class couponsController {
    @Resource
    private goodsService goodsservice;

//    public CommonResult GetCoupons(){
//        return CommonResult.failed();
//    }
    @GetMapping("/getCouponsById")
    public CommonResult getCouponsById(@RequestParam Integer couponsId){
        couponsPO couponsData = goodsservice.getCouponsById(couponsId);
        return CommonResult.success(couponsData);
    }
}
