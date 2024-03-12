package com.limu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.user.dtos.LoginDto;
import com.limu.model.user.pojos.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * TODO 类描述
 *
 * @author: LiMu
 * @createTime: 2023年06月17日 20:42
 */

public interface UserService extends IService<User> {

    /*
    * 登录功能实现
    * */
    public ResponseResult<?> login(LoginDto dto);


    public ResponseResult<?> getUserInfo(String token);

    public ResponseResult<?> logout(String token);

    public ResponseResult<?> getAllUsers();

    public ResponseResult<?> getUserList(String name, String phone, Long pageNo, Long pageSize);

    public void addUser(User user);

    public boolean existsByUserName(String name);

    public ResponseResult<User> getUserById(Integer id);

    void updateUser(User user);

    void deleteUserById(Integer id);

    void getVerifyCode(HttpServletRequest request, HttpServletResponse response);

    public ResponseResult<?>  exportUserList();
}
