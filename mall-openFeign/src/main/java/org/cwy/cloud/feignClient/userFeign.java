package org.cwy.cloud.feignClient;


import org.cwy.cloud.model.CouponsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("user-server")
public interface userFeign {
    @PostMapping("/userCouponsApi/useCoupons")
    public int useCoupons(@RequestBody CouponsDTO coupons);
}
