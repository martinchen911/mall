package com.cf.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.cf.mall.cart.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class MallCartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallCartServiceApplication.class, args);
    }

}
