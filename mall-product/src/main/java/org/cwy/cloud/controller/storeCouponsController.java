package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.CouponsDTO;
import org.cwy.cloud.model.DTO.couponsPageDTO;
import org.cwy.cloud.model.DTO.editCouponsDTO;
import org.cwy.cloud.model.PO.couponsPO;
import org.cwy.cloud.service.couponsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 13:15
 */
@RestController
@RequestMapping("/storeCouponsApi")
public class storeCouponsController {
    @Resource
    private couponsService couponsService;

    @GetMapping("/coupons")
    public CommonResult<List<couponsPO>> getCoupons(couponsPageDTO couponsPage) {
        List<couponsPO> data = couponsService.getCoupons(couponsPage);
        return CommonResult.success(data);
    }

    @PostMapping("/coupons")
    public CommonResult addCoupons(@RequestBody CouponsDTO coupons) {

        couponsService.addCoupons(coupons);
        return CommonResult.addSuccess();
    }

    @DeleteMapping("/coupons")
    public CommonResult deleteCoupons() {
        return CommonResult.deleteSuccess();
    }

    @PutMapping("/coupons")
    public CommonResult editCoupons(@RequestBody editCouponsDTO couponsDTO) {
        couponsService.editCoupons(couponsDTO);
        return CommonResult.editSuccess();
    }


}
