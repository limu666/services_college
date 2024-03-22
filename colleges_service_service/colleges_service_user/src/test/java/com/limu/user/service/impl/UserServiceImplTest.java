package com.limu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.user.dtos.LoginDto;
import com.limu.model.user.pojos.Menu;
import com.limu.model.user.pojos.User;
import com.limu.model.user.pojos.UserRole;
import com.limu.user.mapper.UserRoleMapper;
import com.limu.user.service.MenuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private RedisTemplate<String, User> mockRedisTemplate;
    @Mock
    private RedisTemplate<String, String> mockRedis;
    @Mock
    private UserRoleMapper mockUserRoleMapper;
    @Mock
    private MenuService mockMenuService;

    @InjectMocks
    private UserServiceImpl userServiceImplUnderTest;

    @Test
    void testLogin() {
        // Setup
        final LoginDto dto = new LoginDto();
        dto.setPhone("phone");
        dto.setPassword("password");
        dto.setVerifyCode("verifyCode");

        when(mockRedis.opsForValue()).thenReturn(null);
        when(mockUserRoleMapper.selectList(any(LambdaQueryWrapper.class)))
                .thenReturn(Arrays.asList(new UserRole(0, 0, 0)));
        when(mockRedisTemplate.opsForValue()).thenReturn(null);

        // Run the test
        final ResponseResult<?> result = userServiceImplUnderTest.login(dto);

        // Verify the results
        verify(mockRedisTemplate).delete("verifyCode");
    }

    @Test
    void testLogin_UserRoleMapperReturnsNoItems() {
        // Setup
        final LoginDto dto = new LoginDto();
        dto.setPhone("phone");
        dto.setPassword("password");
        dto.setVerifyCode("verifyCode");

        when(mockRedis.opsForValue()).thenReturn(null);
        when(mockUserRoleMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());
        when(mockRedisTemplate.opsForValue()).thenReturn(null);

        // Run the test
        final ResponseResult<?> result = userServiceImplUnderTest.login(dto);

        // Verify the results
        verify(mockRedisTemplate).delete("verifyCode");
    }

    @Test
    void testLoginApp() {
        // Setup
        final LoginDto dto = new LoginDto();
        dto.setPhone("phone");
        dto.setPassword("password");
        dto.setVerifyCode("verifyCode");

        when(mockRedis.opsForValue()).thenReturn(null);

        // Run the test
        final ResponseResult<?> result = userServiceImplUnderTest.loginApp(dto);

        // Verify the results
        verify(mockRedisTemplate).delete("verifyCode");
    }

    @Test
    void testGetUserInfo() {
        // Setup
        when(mockRedisTemplate.opsForValue()).thenReturn(null);

        // Configure MenuService.getMenuListByUserId(...).
        final Menu menu = new Menu();
        menu.setMenuId(0);
        menu.setComponent("component");
        menu.setPath("path");
        menu.setRedirect("redirect");
        menu.setName("name");
        final List<Menu> menus = Arrays.asList(menu);
        when(mockMenuService.getMenuListByUserId(0)).thenReturn(menus);

        // Run the test
        final ResponseResult<?> result = userServiceImplUnderTest.getUserInfo("token");

        // Verify the results
    }

    @Test
    void testGetUserInfo_MenuServiceReturnsNoItems() {
        // Setup
        when(mockRedisTemplate.opsForValue()).thenReturn(null);
        when(mockMenuService.getMenuListByUserId(0)).thenReturn(Collections.emptyList());

        // Run the test
        final ResponseResult<?> result = userServiceImplUnderTest.getUserInfo("token");

        // Verify the results
    }

    @Test
    void testLogout() {
        // Setup
        // Run the test
        final ResponseResult<?> result = userServiceImplUnderTest.logout("token");

        // Verify the results
        verify(mockRedisTemplate).delete("token");
    }

    @Test
    void testGetAllUsers() {
        assertThat(userServiceImplUnderTest.getAllUsers()).isNull();
    }

    @Test
    void testGetUserList() {
        // Setup
        // Run the test
        final ResponseResult<?> result = userServiceImplUnderTest.getUserList("name", "phone", 0L, 0L);

        // Verify the results
    }

    @Test
    void testAddUser() {
        // Setup
        final User user = new User();
        user.setId(0);
        user.setSalt("");
        user.setName("name");
        user.setPassword("");
        user.setPhone("phone");
        user.setImage("http://localhost:10001/upload/hdpic/c34c2287609347479b9dd24a2cabe918.jpg");
        user.setCertification(false);
        user.setIdentityAuthentication(false);
        user.setRemark("remark");
        user.setRoleIdList(Arrays.asList(0));

        // Run the test
        userServiceImplUnderTest.addUser(user);

        // Verify the results
        verify(mockUserRoleMapper).insert(new UserRole(0, 0, 0));
    }

    @Test
    void testVerifyPassword() {
        assertThat(UserServiceImpl.verifyPassword("inputPassword", "dbPassword", "dbSalt")).isFalse();
    }

    @Test
    void testExistsByUserName() {
        assertThat(userServiceImplUnderTest.existsByUserName("name")).isFalse();
    }

    @Test
    void testGetUserById() {
        // Setup
        when(mockUserRoleMapper.selectList(any(LambdaQueryWrapper.class)))
                .thenReturn(Arrays.asList(new UserRole(0, 0, 0)));

        // Run the test
        final ResponseResult<User> result = userServiceImplUnderTest.getUserById(0);

        // Verify the results
    }

    @Test
    void testGetUserById_UserRoleMapperReturnsNoItems() {
        // Setup
        when(mockUserRoleMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final ResponseResult<User> result = userServiceImplUnderTest.getUserById(0);

        // Verify the results
    }

    @Test
    void testUpdateUser() {
        // Setup
        final User user = new User();
        user.setId(0);
        user.setSalt("");
        user.setName("name");
        user.setPassword("");
        user.setPhone("phone");
        user.setImage("http://localhost:10001/upload/hdpic/c34c2287609347479b9dd24a2cabe918.jpg");
        user.setCertification(false);
        user.setIdentityAuthentication(false);
        user.setRemark("remark");
        user.setRoleIdList(Arrays.asList(0));

        // Run the test
        userServiceImplUnderTest.updateUser(user);

        // Verify the results
        verify(mockUserRoleMapper).delete(any(LambdaQueryWrapper.class));
        verify(mockUserRoleMapper).insert(new UserRole(0, 0, 0));
    }

    @Test
    void testDeleteUserById() {
        // Setup
        // Run the test
        userServiceImplUnderTest.deleteUserById(0);

        // Verify the results
        verify(mockUserRoleMapper).delete(any(LambdaQueryWrapper.class));
    }

    @Test
    void testGetVerifyCode() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        when(mockRedis.opsForValue()).thenReturn(null);

        // Run the test
        userServiceImplUnderTest.getVerifyCode(request, response);

        // Verify the results
        verify(mockRedisTemplate).delete("verifyCode");
    }

    @Test
    void testExportUserList() {
        // Setup
        // Run the test
        final ResponseResult<?> result = userServiceImplUnderTest.exportUserList();

        // Verify the results
    }

    @Test
    void testGenerateSalt() {
        assertThat(UserServiceImpl.generateSalt()).isEqualTo("");
    }

    @Test
    void testHashPassword() {
        assertThat(UserServiceImpl.hashPassword("password", "salt")).isEqualTo("result");
    }
}
