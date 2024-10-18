package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.DTO.CommonPage;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.cartDTO;
import org.cwy.cloud.service.carService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/27 16:26
 */
@RestController
@RequestMapping("/carApi")
public class carController {
    @Resource
    private carService carService;

    @GetMapping("/car")
    public CommonResult getCar(CommonPage page) {
       Map<String, Object> carData =  carService.getCar(page);
       return CommonResult.success(carData);
    }

    @PostMapping("/car")
    public CommonResult addCar(@RequestBody cartDTO data) {
        boolean carData =  carService.addCar(data);
        return CommonResult.addSuccess();
    }

    @PutMapping("/car")
    public CommonResult EdicCart(@RequestBody cartDTO data) {
//        System.out.println(data);
        boolean carData =  carService.EditCart(data);
        return CommonResult.addSuccess();
    }

    @DeleteMapping ("/car")
    public CommonResult DeleteCart(@RequestBody cartDTO data) {
//        System.out.println(data);
        boolean carData =  carService.DeleteCart(data);
        return CommonResult.addSuccess();
    }
}
