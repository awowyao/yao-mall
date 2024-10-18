package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.modle.PO.addressPO;
import org.cwy.cloud.service.userService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class userController {
    @Resource
    private userService userService;

    @GetMapping("/GetAddress")
    public CommonResult<addressPO> GetAddress(){
        Integer id = 1;
        addressPO data = userService.GetAddress(id);
        return CommonResult.success(data);
    }

    @GetMapping("/concernStore")
    public CommonResult concernStore(Integer storeId){
        Integer id = 1;
        Integer data = userService.concernStore(id ,storeId);
        return CommonResult.success(data);
    }

    @GetMapping("/storeGetFens")
    public List<Integer> storeGetFens(Integer storeId){

        return userService.storeGetFens(storeId);
    }
}
