package org.cwy.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 17:59
 */
@FeignClient("mall-snapping-up-server")
public interface snappingUpFeign {
    @GetMapping("/snappingUpApi/getSkuPrice")
    public Double getSkuPrice(@RequestParam Integer SkuId);
}
