package com.cf.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.cf.mall.order.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class MallOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderServiceApplication.class, args);
    }

}
