package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.snappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpOrderDTO;
import org.cwy.cloud.model.DTO.snappingUpPageDTO;
import org.cwy.cloud.service.snappingUpService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/snappingUpApi")
public class snappingUpController {
    @Resource
    private snappingUpService snappingUpService;

    @PostMapping("/getSnappingUp")
    public CommonResult getSnappingUp(@RequestBody snappingUpPageDTO snappingUpPageDTO) {
        Map<String, Object> data = snappingUpService.getSnappingUp(snappingUpPageDTO);
        return CommonResult.success(data);
    }
    @GetMapping("/SnappingUp")
    public CommonResult getSnappingUp(Integer snappingId) {
        Map<String, Object> data = snappingUpService.getSnappingUpById(snappingId);
        return CommonResult.success(data);
    }

    @GetMapping("/getSkuPrice")
    public Double getSkuPrice(Integer SkuId) {
        Double Price = snappingUpService.getSkuPrice(SkuId);
        return Price;
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
