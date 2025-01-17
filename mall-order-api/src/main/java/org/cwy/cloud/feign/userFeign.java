package org.cwy.cloud.feign;

import org.cwy.cloud.DTO.CouponsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-server")
public interface userFeign {
    @PostMapping("/userCouponsApi/useCoupons")
    public int useCoupons(@RequestBody CouponsDTO coupons);
}
