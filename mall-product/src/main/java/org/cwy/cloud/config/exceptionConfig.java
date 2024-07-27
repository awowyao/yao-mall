package org.cwy.cloud.config;

import org.cwy.cloud.common.api.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/26 18:32
 */
@ControllerAdvice
public class exceptionConfig {
    @ExceptionHandler(value =IllegalStateException.class)
    @ResponseBody
    public CommonResult IllegalStateExceptionHandler(IllegalStateException e){
//        System.out.println("全局异常捕获>>>:"+);
        return CommonResult.failed(403, e.getMessage());
    }
//    @ExceptionHandler(value =Exception.class)
//    @ResponseBody
//    public CommonResult exceptionHandler(Exception e){
//        System.out.println("全局异常捕获>>>:"+e);
//        return CommonResult.failed(403, "异常");
//    }
}
