package com.chan.platform.starter.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 10:10
 * FileName: LongToStringConfig
 * Description: 防止Long型数据精度丢失
 */
@Configuration
public class LongToStringConfig {
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder mapperBuilder) {
        ObjectMapper build = mapperBuilder.createXmlMapper(false).build();
        build.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        build.registerModule(module);
        return build;

    }
}
