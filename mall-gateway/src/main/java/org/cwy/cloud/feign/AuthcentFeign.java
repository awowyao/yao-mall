package org.cwy.cloud.feign;

import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "spring-oauth-server")
public interface AuthcentFeign {
    @GetMapping("/authcenterApi/getUserAuto")
    public JsonObject getUserAuto();
}
