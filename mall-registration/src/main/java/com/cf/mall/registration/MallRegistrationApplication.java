package com.cf.mall.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MallRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallRegistrationApplication.class, args);
    }

}
