package org.cwy.cloud.feign;

import org.cwy.cloud.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mall-product")
public interface goodsFeign {
    @GetMapping("/goodsApi/getGoodsById")
    public CommonResult getGoodsById(@RequestParam(value = "goosdId", defaultValue = "1") Integer goosdId);
    @GetMapping("/subtractInventory")
    public int subtractInventory(@RequestParam Integer goodsId, @RequestParam Integer buyNub);

    @GetMapping("/couponsApi/getCouponsById")
    public CommonResult getCouponsById(@RequestParam Integer couponsId);
}
