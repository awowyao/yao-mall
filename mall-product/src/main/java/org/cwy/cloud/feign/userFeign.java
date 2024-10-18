package org.cwy.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("user-server")
public interface userFeign {
    @GetMapping("/storeGetFens")
    List<Integer> storeGetFens(@RequestParam Integer storeId);
}
