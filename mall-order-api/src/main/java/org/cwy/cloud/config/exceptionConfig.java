package org.cwy.cloud.config;

import org.cwy.cloud.common.api.CommonResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class exceptionConfig {

//    @ExceptionHandler(value = RuntimeException.class)
//    public CommonResult IllegalArgumentExceptionHandler(IllegalArgumentException e){
////        System.out.println("全局异常捕获>>>:"+e.getMessage());
//        return CommonResult.failed(403, e.getMessage());
//    }
//
//    @ExceptionHandler(value =Exception.class)
//    public CommonResult exceptionHandler(Exception e){
//        System.out.println("全局异常捕获>>>:"+e.getClass());
//        return CommonResult.failed(403, "服务器异常");
//    }
}
