package com.limu.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableOpenApi
@EnableDiscoveryClient
@MapperScan("com.limu.user.mapper")
@EnableFeignClients(basePackages = "com.limu.apis")
@SpringBootApplication
public class CollegesServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegesServiceUserApplication.class, args);
    }

}
