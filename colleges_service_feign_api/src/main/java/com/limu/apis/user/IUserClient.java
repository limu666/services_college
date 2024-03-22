package com.limu.apis.user;

import com.limu.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "colleges-user")
public interface IUserClient {

    @GetMapping("/api/v1/manage/user/getUserById/{id}")
    ResponseResult<?> getUserById(@PathVariable("id") Integer id);

    @GetMapping("/hello")
    String hello();
}
