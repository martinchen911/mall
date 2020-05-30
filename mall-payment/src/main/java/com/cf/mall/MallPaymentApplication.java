package com.cf.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.cf.mall.payment.mapper")
@EnableFeignClients("com.cf.mall.service")
@EnableDiscoveryClient
@SpringBootApplication
public class MallPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallPaymentApplication.class, args);
    }

}
