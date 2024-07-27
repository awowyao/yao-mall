package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.snappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpOrderDTO;
import org.cwy.cloud.service.snappingUpService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/snappingUpApi")
public class snappingUpController {
    @Resource
    private snappingUpService snappingUpService;

    @PostMapping("/addSnapping")
    public CommonResult<String> addSnappingUp(@RequestBody snappingUpDTO snappingUp) {
        Integer state = snappingUpService.addSnappingUp(snappingUp);
        if (state ==1){
            return CommonResult.success("1");
        }
        return CommonResult.failed();
    }

    @PostMapping("/addSnappingUpOrder")
    public CommonResult<String> addSnappingUpOrder(@RequestBody snappingUpOrderDTO snappingUpOrder) {
        Integer state = snappingUpService.addSnappingUpOrder(snappingUpOrder);
        if (state ==1){
            return CommonResult.success("1");
        }
        return CommonResult.failed();
    }
}
