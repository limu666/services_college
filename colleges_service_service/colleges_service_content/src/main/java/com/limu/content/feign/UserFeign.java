package com.limu.content.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("colleges-user")
public interface UserFeign {

    @GetMapping("/user/hello")
    String hello();

}
