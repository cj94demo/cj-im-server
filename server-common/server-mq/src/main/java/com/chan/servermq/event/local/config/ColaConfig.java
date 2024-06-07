package com.chan.servermq.event.local.config;

import com.alibaba.cola.boot.SpringBootstrap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 16:20
 * FileName: ColaConfig
 * Description: COLA框架的配置
 */
@Configuration
@ComponentScan(value = {"com.alibaba.cola"})
public class ColaConfig {
    @Bean(initMethod = "init")
    public SpringBootstrap bootstrap() {
        SpringBootstrap bootstrap = new SpringBootstrap();
        return bootstrap;
    }
}
