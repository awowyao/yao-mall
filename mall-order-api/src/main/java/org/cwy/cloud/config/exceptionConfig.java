package org.cwy.cloud.config;

import org.cwy.cloud.common.api.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class exceptionConfig {
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public CommonResult exceptionHandler(Exception e){
        System.out.println("全局异常捕获>>>:"+e);
        return CommonResult.failed(403, "服务器异常");
    }
}
