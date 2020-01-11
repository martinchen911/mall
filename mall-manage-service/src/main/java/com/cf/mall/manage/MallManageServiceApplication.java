package com.cf.mall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.cf.mall.manage.mapper")
public class MallManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallManageServiceApplication.class, args);
    }

}
