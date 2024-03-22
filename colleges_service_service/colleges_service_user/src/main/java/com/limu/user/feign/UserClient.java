package com.limu.user.feign;

import com.limu.apis.user.IUserClient;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserClient implements IUserClient {

    @Resource
    private UserService userService;

    @Override
    @ApiOperation(value = "通过ID获取用户信息")
    @GetMapping("/api/v1/manage/user/getUserById/{id}")
    public ResponseResult<?> getUserById(@PathVariable("id") Integer id){

        System.out.println("sssssssssssssss");
        return userService.getUserById(id);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

}
