package org.cwy.cloud.feignClient;


import org.cwy.cloud.model.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mall-product")
public interface goodsFeign {
    @GetMapping("/goodsApi/getGoodsById")
    public CommonResult getGoodsByIds(@RequestParam Integer goodsId);
    @GetMapping("/subtractInventory")
    public int subtractInventory(@RequestParam Integer goodsId, @RequestParam Integer buyNub);

    @GetMapping("/couponsApi/getCouponsById")
    public CommonResult getCouponsById(@RequestParam Integer couponsId);

    @GetMapping("/couponsApi/checkCouponsById")
    public Map<String, Object> checkCouponsById(@RequestParam Integer gid, @RequestParam Integer couponsId);
}
