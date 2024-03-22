package com.limu.user.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.user.dtos.LoginDto;
import com.limu.model.user.pojos.User;
import com.limu.user.config.CreateVerifiCodeImage;
import com.limu.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 类描述
 *
 * @author: LiMu
 * @createTime: 2023年06月17日 20:27
 */
@Api(value = "登录操作", tags = "后台系统用户登录")
@Slf4j
@RestController     //默认做JSON处理
@RequestMapping("/api/v1/login")
public class UserLoginController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifyCodeImage")
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response){

        userService.getVerifyCode(request, response);

    }

    @PostMapping("/login_auth")
    @ApiOperation(value = "用户登录")
    public ResponseResult<?> login(@RequestBody LoginDto dto){

        return userService.login(dto);
    }

    @PostMapping("/login_authApp")
    @ApiOperation(value = "App登录")
    public ResponseResult<?> loginApp(@RequestBody LoginDto dto){

        return userService.loginApp(dto);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public ResponseResult<?> getUserInfo(@RequestParam("token") String token){
        return userService.getUserInfo(token);
    }

    //logout退出
    @ApiOperation(value = "用户退出")
    @PostMapping("/logout")
    public ResponseResult<?> logout(@RequestHeader("Z-Token") String token){
        return userService.logout(token);
    }

}











