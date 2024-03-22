package com.limu.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@EnableDiscoveryClient
@MapperScan("com.limu.content.mapper")
@EnableFeignClients(basePackages = "com.limu.apis")
@SpringBootApplication
public class CollegesServiceContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegesServiceContentApplication.class, args);
    }

}
