package com.chan.platform.user.starter;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class PlatformUserStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformUserStarterApplication.class, args);
    }

}
