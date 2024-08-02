package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.modle.DTO.addressDTO;
import org.cwy.cloud.modle.DTO.addressPageDTO;
import org.cwy.cloud.modle.PO.addressPO;
import org.cwy.cloud.service.addressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: 用户地址管理
 * @date 2024/8/1 15:59
 */

@RestController
@RequestMapping("/userAddressApi")
public class addressController {
    @Resource
    private addressService addressService;
    @GetMapping("/address")
    public CommonResult getAddress(addressPageDTO addressPage){
        List<addressPO> data = addressService.getAddress(addressPage);

        return CommonResult.success(data);
    }

    @PostMapping("/address")
    public CommonResult addAddress(@RequestBody addressDTO requestData){
        addressService.addAddress(requestData);
        return CommonResult.addSuccess();
    }

    @PutMapping("/address")
    public CommonResult editAddress(@RequestBody addressDTO requestData){
        addressService.editAddress(requestData);
        return CommonResult.editSuccess();
    }
}
