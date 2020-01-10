package com.cf.mall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.cf.mall.user.mapper")
@SpringBootApplication
public class MallUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUserServiceApplication.class, args);
    }

}
