package com.limu.user.controller.v1;

import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.common.enums.AppHttpCodeEnum;
import com.limu.model.user.pojos.User;
import com.limu.model.user.pojos.UserRole;
import com.limu.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * APP用户信息表 前端控制器
 * </p>
 *
 * @author limu
 * @since 2023-07-21
 */

@Api(value = "用户信息管理", tags = "后台系统用户管理")
@Slf4j
@RestController     //默认做JSON处理
@RequestMapping("/api/v1/manage/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder; // 注入密码编码器

    @ApiOperation(value = "测试类", tags = {"测试"})
    @GetMapping("/test")
    public void test(){
        log.info("居然失败了。。。。。。");
    }

    /*
    参数

    * */
    @ApiOperation(value = "查询用户", tags = {"需要优化"})
    @GetMapping("/list")
    public ResponseResult<?> getUserList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "pageNo", required = true) Long pageNo,
            @RequestParam(value = "pageSize", required = true) Long pageSize){

        if (name == null) {
            name = "";
        }
        if (phone == null) {
            phone = "";
        }

        return userService.getUserList(name, phone, pageNo, pageSize);
       /* LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(org.springframework.util.StringUtils.hasLength(name), User::getName, name);
        wrapper.eq(org.springframework.util.StringUtils.hasLength(phone), User::getPhone, phone);

        if(StringUtils.hasLength(name) || StringUtils.hasLength(phone)){
            pageNo = 1L;
        }

        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());*/

        /*System.out.println("通过iterator遍历所有的value,但是不能遍历key");
        for(Map.Entry<String, Object> entry : data.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + ":" + value);
        }*/

        /*if(StringUtils.hasLength(name) || StringUtils.hasLength(phone)){
            name = null;
            phone = null;
            return userService.getUserList(name, phone, pageNo, pageSize);
        }
        return userService.getUserList(name, phone, pageNo, pageSize);*/

        //return ResponseResult.okResult(data);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/addUser")
    public ResponseResult<?> addUser(@RequestBody User user){
        try{
            if(user == null){
                //验证用户输入是否为空
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
            // 对用户输入进行过滤和转义，防止跨站点脚本攻击
            user.setName(HtmlUtils.htmlEscape(user.getName()));

            // 强化密码，确保密码的安全性，包括密码的复杂性和加密存储
            //user.setPassword(passwordEncoder.encode(user.getPassword()));

            // 防止重复提交
            if(!userService.existsByUserName(user.getName())) { // 查询数据库是否存在相同的手机号
                return ResponseResult.errorResult(20001,"用户名重复");
            }
            List<Integer> roleIdList = user.getRoleIdList();
            if(roleIdList.size() == 0){
                return ResponseResult.errorResult(20001,"至少选择一个身份");
            }

            userService.addUser(user);
            return ResponseResult.okResult("添加用户成功");
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/updateUser")
    public ResponseResult<?> updateUser(@RequestBody User user){
        user.setPassword(null);
        user.setSalt(null);
        userService.updateUser(user);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @ApiOperation(value = "通过id获取用户信息")
    @GetMapping("/getUserById/{id}")
    public ResponseResult<User> getUserById(@PathVariable("id") Integer id){


        return userService.getUserById(id);
    }

    @ApiOperation(value = "删除用户信息（逻辑删除）")
    @GetMapping("/deleteUserById/{id}")
    public ResponseResult<?> deleteUserById(@PathVariable("id") Integer id){

        userService.deleteUserById(id);

        return ResponseResult.okResult(20000 ,"删除成功");
    }

    @ApiOperation(value = "获取头像")
    @PostMapping("/headerImgUpload")
    public ResponseResult<?> headerImgUpload(@RequestPart("image") MultipartFile multipartFile) throws IOException {
        // 先上传文件的名称
        String originalFilename = multipartFile.getOriginalFilename();
        // 获取文件格式 即后缀，如：jpg png jpeg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 为了避免文件保存到服务器时 文件名相同的冲突 导致文件覆盖 所以使用UUID随机生成数
        String fileName = UUID.randomUUID().toString().toLowerCase().replace("-", "").concat(suffix);
        // 保存路径
        String destPath = ("D:\\桌面文件\\存放\\练习项目\\main\\one\\colleges_service\\colleges_service_service" +
                "\\colleges_service_user\\src\\main\\resources\\static\\upload\\hdpic\\").concat(fileName);
        // 进行文件保存到服务端
        multipartFile.transferTo(new File(destPath));

        return ResponseResult.okResult("upload/hdpic/".concat(fileName));
    }

    @ApiOperation(value = "导出表格")
    @GetMapping("/export")
    public ResponseResult<?> export(){

        return userService.exportUserList();

    }

}
