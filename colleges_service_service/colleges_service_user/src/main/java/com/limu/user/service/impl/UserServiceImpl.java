package com.limu.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limu.file.ExcelUtils;
import com.limu.file.ExportCsv;
import com.limu.file.MyCsvFileUtil;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.common.enums.AppHttpCodeEnum;
import com.limu.model.user.dtos.LoginDto;
import com.limu.model.user.pojos.Menu;
import com.limu.model.user.pojos.User;
import com.limu.model.user.pojos.UserRole;
import com.limu.user.config.CreateVerifiCodeImage;
import com.limu.user.mapper.UserMapper;
import com.limu.user.mapper.UserRoleMapper;
import com.limu.user.service.MenuService;
import com.limu.user.service.UserService;

import com.limu.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * TODO 类描述
 *
 * @author: LiMu
 * @createTime: 2023年06月17日 20:43
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    public static final String IMAGE = "http://localhost:10001/upload/hdpic/c34c2287609347479b9dd24a2cabe918.jpg";

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Resource
    private RedisTemplate<String, String> redis;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Autowired
    private MenuService menuService;

    //后台登录
    @Override
    public ResponseResult<?> login(LoginDto dto) {
        //进行验证码验证
        String verifyCode = redis.opsForValue().get("verifyCode");

        if("".equals(dto.getVerifyCode()) || null == dto.getVerifyCode()){
            return ResponseResult.errorResult(20004,"验证码有误，请刷新重新输入");
        }
        if("".equals(verifyCode) || null == verifyCode){
            return ResponseResult.errorResult(20004,"验证码有误，请刷新重新输入");
        }
        if(!verifyCode.equals(dto.getVerifyCode())){
            return ResponseResult.errorResult(20004,"验证码有误，请刷新重新输入");
        }

        redisTemplate.delete("verifyCode");

        //1.根据手机号和密码
        if(StringUtils.isNotBlank(dto.getPhone())&& StringUtils.isNotBlank(dto.getPassword())){

            //1.1根据手机号查询用户信息
            User dbUser = getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, dto.getPhone()));
            if(dbUser == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户信息不存在");
            }
//            System.out.println("2024年1月11日12:24:41");
            //1.2比对密码
            // 用户输入的用户名和密码
            String inputPassword = dto.getPassword();
            // 数据库中的salt和password
            String dbSalt = dbUser.getSalt();
            String dbPassword = dbUser.getPassword();
            // 从数据库中获取存储的salt，并将用户输入的密码与存储的salt进行验证
            if (!verifyPassword(inputPassword, dbPassword, dbSalt)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            /*String salt = dbUser.getSalt();
            String password = dto.getPassword();
            String pwd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            //System.out.println(password + "====" + pwd);
            if(!pwd.equals(dbUser.getPassword())){
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }*/
            //1.3 返回数据 jwt user
            String token = AppJwtUtil.getToken(dbUser.getId().longValue());
            //redisTemplate.opsForValue().set(key, dbUser, 30, TimeUnit.MINUTES);
            // 指定泛型类型参数
            ValueOperations<String, User> valueOps = redisTemplate.opsForValue();
            valueOps.set(token, dbUser, 30, TimeUnit.MINUTES);//存入用户对象，设置30分钟时效
            Map<String, Object> map = new HashMap<>();
            dbUser.setPassword("");
            dbUser.setSalt("");
            map.put("user", dbUser);
            map.put("token", token);

            return ResponseResult.okResult(map);

        }
        /*else{
            //2.游客登录
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(0L));
            return ResponseResult.okResult(map);
        }*/

        return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
    }


    //获取用户信息
    @Override
    public ResponseResult<?> getUserInfo(String token) {
        //System.out.println(token);
        //根据token来获取用户信息，从redis,进行反序列化
        if(token == null){
            ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_INVALID);
        }

        Object obj = redisTemplate.opsForValue().get(token);
        if(obj != null){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("id",loginUser.getId());
            data.put("name", loginUser.getName());
            data.put("avatar", loginUser.getImage());

            //角色
            //List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            //List<String> roleList =
            data.put("remark", loginUser.getRemark());

            // 权限列表
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList", menuList);

            return ResponseResult.okResult(data);
        }

        return ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_INVALID);
    }

    //退出
    @Override
    public ResponseResult<?> logout(String token) {
        //判断是否为空
        if(token == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //判断参数异常
        try {
            redisTemplate.delete(token);
            return ResponseResult.okResult(20000, "退出登录");
        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_INVALID);
        }
    }

    //获取用户信息
    @Override
    public ResponseResult<?> getAllUsers() {



        return null;
    }

    //获取用户列表
    @Override
    public ResponseResult<?> getUserList(String name, String phone, Long pageNo, Long pageSize) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(org.springframework.util.StringUtils.hasLength(name), User::getName, name);
        wrapper.eq(org.springframework.util.StringUtils.hasLength(phone), User::getPhone, phone);

        if(name.length() != 0 || phone.length() != 0){
            pageNo = 1L;
        }

        Page<User> page = new Page<>(pageNo, pageSize);
        this.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        /*if(org.springframework.util.StringUtils.hasLength(phone) || org.springframework.util.StringUtils.hasLength(name)){
            pageNo = 1L;
        }

        //User userList = this.baseMapper.userList(name, phone, pageNo, pageSize);
        int id = 1;
        List<User> user = this.baseMapper.userList(pageNo, pageSize);

        log.info("ok");*/


        return ResponseResult.okResult(data);
    }

    // 添加用户
    @Override
    public void addUser(User user) {

        // 生成随机的salt
        String salt = generateSalt();

        // 将密码和salt拼接在一起
        //String passwordWithSalt = user.getPassword() + salt;

        // 使用SHA-256进行加密
        String hashedPassword = hashPassword(user.getPassword(), salt);
        user.setPassword(hashedPassword);
        // 存储hashedPassword和salt在数据库中
        //System.out.println("Hashed Password: " + hashedPassword);
        //System.out.println("Salt: " + byteArrayToHexString(salt));
        // 加盐
        user.setSalt(salt);
        // 是否确认
        user.setCertification(false);
        // 是否是否验证
        user.setIdentityAuthentication(false);
        // 身份
        //user.setFlag((short) 0);
        // 获取当前时间
        user.setCreatedTime();
        // 设置默认图片
        //user.setImage(IMAGE);
        if(user.getImage().equals("")){
            user.setImage(IMAGE);
        }
        //保存用户
        //this.save(user);
        // 写入用户表
        this.baseMapper.insert(user);
        // 写入用户角色表
        List<Integer> roleIdList = user.getRoleIdList();
        if(roleIdList != null){
            for (Integer roleId : roleIdList) {
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }

    }

    // 根据数据库中存储的密码和salt验证用户输入的密码
    public static boolean verifyPassword(String inputPassword, String dbPassword, String dbSalt) {

        if (inputPassword == null || dbPassword == null || dbSalt == null) {
            return false; // 如果有任何一个为空，则无法验证，返回false
        }

        // 将用户输入的密码和数据库中存储的salt进行加密
        String hashedInputPassword = hashPassword(inputPassword, dbSalt);

        // 比较加密后的密码与数据库中存储的密码是否一致
        return hashedInputPassword.equals(dbPassword);
    }

    // 验证是否是唯一手机号
    @Override
    public boolean existsByUserName(String name) {
        //1.1根据手机号查询用户
        User dbUser = getOne(Wrappers.<User>lambdaQuery().eq(User::getName, name));
        if(dbUser == null){
            return true;
        }
        return false;
    }

    @Override
    public ResponseResult<User> getUserById(Integer id) {
        User user = this.baseMapper.selectById(id);
        if(user == null){
            ResponseResult.errorResult(20001,"未成功查询，请刷新页面后重新操作");
        }
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);

        List<Integer> roleIdList = userRoleList.stream()
                                        .map(userRole -> {return userRole.getRoleId();})
                                        .collect(Collectors.toList());
        user.setRoleIdList(roleIdList);

        return ResponseResult.okResult(user);
    }

    @Override
    public void updateUser(User user) {
        // 更新用户表
        this.baseMapper.updateById(user);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userRoleMapper.delete(wrapper);
        // 设置新的角色
        List<Integer> roleIdList = user.getRoleIdList();
        if(roleIdList != null){
            for (Integer roleId : roleIdList) {
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }

    }

    @Override
    public void deleteUserById(Integer id) {
        // 删除用户表
        this.baseMapper.deleteById(id);
        //清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        userRoleMapper.delete(wrapper);
    }

    @Override
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        redisTemplate.delete("verifyCode");
        // 获取图片
        BufferedImage verifyCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        // 获取图片上的验证码
        String verifyCode =new String( CreateVerifiCodeImage.getVerifiCode());
        // 将验证码文本放入redis,为下一次验证做准备
//        HttpSession session = request.getSession();
//        session.setAttribute("verifyCode",verifyCode);
        //String verifyCodes = (String)session.getAttribute("verifyCode");

        // 将验证码存入redis里面
        ValueOperations<String, String> valueOps = redis.opsForValue();
        valueOps.set("verifyCode", verifyCode, 30,TimeUnit.SECONDS);
        // 将验证码图片响应给浏览器
        try {
            ImageIO.write(verifyCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseResult<?> exportUserList() {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        List<User> users = this.baseMapper.selectList(wrapper);

        String fileName = ExportCsv.exportCsv(users);

        /*//存放地址&文件名
        String fileName = "D:\\Java学习\\colleges_export\\"+ MyCsvFileUtil.buildCsvFileFileName(users);
        //创建表格行标题
        String tableNames = ExcelUtils.buildCsvFileTableNamesNew(ExcelUtils.resolveExcelTableName(users.get(0)));
        //创建文件
        MyCsvFileUtil.writeFile(fileName, tableNames);
        //写入数据
        String contentBody = MyCsvFileUtil.buildCsvFileBodyMap(users);
        //调用方法生成
        MyCsvFileUtil.writeFile(fileName, contentBody);*/

        return ResponseResult.okResult("导出成功！！！目录地址为：".concat(fileName));
    }

    // 生成随机的salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[5];
        random.nextBytes(saltBytes);
        return bytesToHex(saltBytes);
    }

    // 使用SHA-256进行加密，并将结果截取一部分使其长度为5字节，然后转换为十六进制字符串后返回最多长度为16
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwordWithSalt = password + salt;
            byte[] hashedPasswordBytes = md.digest(passwordWithSalt.getBytes());

            // 将SHA-256生成的32字节长度的摘要截取一部分使其长度为5字节
            byte[] truncatedBytes = new byte[5];
            System.arraycopy(hashedPasswordBytes, 0, truncatedBytes, 0, 5);
            return bytesToHex(truncatedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将byte数组转换为十六进制的字符串
    // 将字节数组转换为十六进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    /*public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }*/
}
