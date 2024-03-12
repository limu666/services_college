package com.limu.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@EnableDiscoveryClient
@MapperScan("com.limu.content.mapper")
@SpringBootApplication
public class CollegesServiceContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegesServiceContentApplication.class, args);
    }

}
