package com.chan.platform.friend.starter;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDubbo
@EnableDiscoveryClient
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan(basePackages = {"com.chan.platform.friend.domain.repository"})
@ComponentScan(basePackages = {"com.chan"})
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })// 禁用secrity
public class PlatformFriendStarterApplication {

    public static void main(String[] args) {
        System.setProperty("user.home", "D:/cj-im/platform-friend");
        SpringApplication.run(PlatformFriendStarterApplication.class, args);
    }

}
