package org.cwy.cloud.controller;

import jakarta.annotation.Resource;

import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.common.api.CommonResult;

import org.cwy.cloud.service.userService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userCouponsApi")
public class couponsController {
    @Resource
    private userService userService;
    @PostMapping("/useCoupons")
    public int useCoupons(@RequestBody CouponsDTO coupons) {
        return userService.useCoupons(coupons);
    }
}
