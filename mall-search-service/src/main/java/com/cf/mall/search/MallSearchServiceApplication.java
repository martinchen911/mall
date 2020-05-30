package com.cf.mall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.cf.mall.search.mapper")
@EnableFeignClients("com.cf.mall.service")
@EnableDiscoveryClient
@SpringBootApplication
public class MallSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallSearchServiceApplication.class, args);
    }

}
