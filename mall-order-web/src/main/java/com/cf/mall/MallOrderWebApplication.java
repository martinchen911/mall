package com.cf.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.cf.mall.service")
@EnableDiscoveryClient
@SpringBootApplication
public class MallOrderWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderWebApplication.class, args);
    }

}
