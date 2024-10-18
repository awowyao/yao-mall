package org.cwy.cloud.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/29 17:14
 */
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        Enumeration<String> enumeration = request.getHeaderNames();
//        while (enumeration.hasMoreElements()) {
//            String name	= enumeration.nextElement();
//            String value = request.getHeader(name);
//            System.out.println(name);
//            System.out.println(value);
//        }
        //添加token
        requestTemplate.header("authorization", request.getHeader("authorization"));
    }
}

