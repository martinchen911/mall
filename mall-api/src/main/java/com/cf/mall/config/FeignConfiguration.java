package com.cf.mall.config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen
 * 2020/5/28
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public Request.Options options(){
        return new Request.Options(5000,10000);
    }
}
