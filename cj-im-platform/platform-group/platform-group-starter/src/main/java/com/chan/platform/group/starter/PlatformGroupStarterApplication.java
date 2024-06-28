package com.chan.platform.group.starter;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class PlatformGroupStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformGroupStarterApplication.class, args);
    }

}
