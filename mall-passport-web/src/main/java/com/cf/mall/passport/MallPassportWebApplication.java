package com.cf.mall.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.cf.mall.service")
@EnableDiscoveryClient
@SpringBootApplication
public class MallPassportWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallPassportWebApplication.class, args);
    }

}
