package org.cwy.cloud.config;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/22 16:41
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3,10,1, TimeUnit.MINUTES,new ArrayBlockingQueue<>(5));
        return poolExecutor;
    }

}
