package com.cf.mall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.cf.mall.search.mapper")
@SpringBootApplication
public class MallSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallSearchServiceApplication.class, args);
    }

}
