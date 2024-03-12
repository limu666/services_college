package com.limu.app.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient  //开启注册中心
@SpringBootApplication
public class CollegesServiceAppGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegesServiceAppGatewayApplication.class, args);
    }

}
