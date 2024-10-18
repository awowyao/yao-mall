package org.cwy.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Component
@FeignClient(value="mall-uniqid")
public interface uniqidFeign {
    @GetMapping("/GetId")
    public int GetId(@RequestParam(value="tag", defaultValue = "1") Integer tag);

}
