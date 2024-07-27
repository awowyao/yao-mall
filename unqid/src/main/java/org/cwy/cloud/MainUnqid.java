package org.cwy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MainUnqid {
    public static void main(String[] args) {
        SpringApplication.run(MainUnqid.class, args);
    }
}