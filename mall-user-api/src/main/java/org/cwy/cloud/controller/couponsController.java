package org.cwy.cloud.controller;

import jakarta.annotation.Resource;

import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.DTO.commonCouponsPO;
import org.cwy.cloud.common.api.CommonResult;

import org.cwy.cloud.service.userService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userCouponsApi")
public class couponsController {
    @Resource
    private userService userService;
    @PostMapping("/useCoupons")
    public int useCoupons(@RequestBody CouponsDTO coupons) {
        return userService.useCoupons(coupons);
    }

    @PostMapping("/getCoupons")
    public CommonResult getCoupons(@RequestBody CouponsDTO coupons) {
        userService.getCoupons(coupons);
        return CommonResult.success("领取成功");
    }

    @GetMapping("/getCouponsList")
    public CommonResult getCouponsList() {
        List<Map<String, Object>> data = userService.getCouponsList();
        return CommonResult.success(data);
    }
}
