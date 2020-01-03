package com.cf.mall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 *
 * @Author chen
 * @Date 2019/12/31
 */
@MapperScan("com.cf.mall.user.mapper")
@SpringBootApplication
public class MallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUserApplication.class, args);
    }

}
