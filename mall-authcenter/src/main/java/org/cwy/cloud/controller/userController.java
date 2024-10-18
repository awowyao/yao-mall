package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.storeUserDTO;
import org.cwy.cloud.model.userSignDTO;
import org.cwy.cloud.service.userService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/24 14:41
 */
@RestController
@RequestMapping("/user/api/")
public class userController {
    @Resource
    private userService userService;


    @PostMapping("userSign")
    public CommonResult userSign(@RequestBody userSignDTO userData) {
        boolean user = userService.userSign(userData);
        return CommonResult.success(user);
    }

    @PostMapping("storeUserSign")
    public CommonResult storeUserSign(@RequestBody storeUserDTO userData) {
        boolean user = userService.storeUserSign(userData);
        return CommonResult.success(user);
    }
}
