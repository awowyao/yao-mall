package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.model.DTO.orderDTO;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.service.returnableService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderApi")
public class returnableController {
    @Resource
    private returnableService returnableService;

    @PostMapping("/requestReturnable")
    public CommonResult<String> requestReturnable(@RequestBody orderDTO orderData ){
        int steate  = returnableService.requestReturnable(orderData);
        if (steate==1){
            return CommonResult.success("成功");
        }else {
            return CommonResult.failed();
        }

    }



}
