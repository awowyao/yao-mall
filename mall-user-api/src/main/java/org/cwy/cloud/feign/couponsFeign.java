package org.cwy.cloud.feign;

import org.cwy.cloud.DTO.commonCouponsPO;
import org.cwy.cloud.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/30 16:21
 */
@FeignClient("mall-product")
public interface couponsFeign {
    @GetMapping("/couponsApi/getCouponsById")
    public CommonResult getCouponsById(@RequestParam Integer couponsId);
}
