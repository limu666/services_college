package com.limu.user.controller.v1;


import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.common.enums.AppHttpCodeEnum;
import com.limu.model.user.pojos.Role;
import com.limu.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * <p>
 * APP角色信息表 前端控制器
 * </p>
 *
 * @author limu
 * @since 2023-07-21
 */

@Api(value = "角色信息管理", tags = "后台系统角色管理")
@Slf4j
@RestController     //默认做JSON处理
@RequestMapping("/api/v1/manage/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "测试类", tags = {"测试"})
    @GetMapping("/test")
    public void test(){
        log.info("居然失败了。。。。。。");
    }

    /*
    参数

    * */
    @ApiOperation(value = "查询角色或按条件查询", tags = {"需要优化"})
    @GetMapping("/list")
    public ResponseResult<?> getRoleList(
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "pageNo", required = true) Long pageNo,
            @RequestParam(value = "pageSize", required = true) Long pageSize){

        if (roleName == null) {
            roleName = "";
        }

        return roleService.getRoleList(roleName, pageNo, pageSize);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/addRole")
    public ResponseResult<?> addRole(@RequestBody Role role){
        try{
            if(role == null){
                //验证用户输入是否为空
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
            // 对用户输入进行过滤和转义，防止跨站点脚本攻击
            role.setRoleName(HtmlUtils.htmlEscape(role.getRoleName()));
            role.setRoleDesc(HtmlUtils.htmlEscape(role.getRoleDesc()));

            // 强化密码，确保密码的安全性，包括密码的复杂性和加密存储
            //role.setPassword(passwordEncoder.encode(role.getPassword()));

            // 防止重复提交
            if(!roleService.existsByRoleName(role.getRoleName())) { // 查询数据库是否存在相同的角色名称
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
            }

            roleService.addRole(role);

            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

    }

    @ApiOperation(value = "修改角色信息")
    @PutMapping("/updateRole")
    public ResponseResult<?> updateRole(@RequestBody Role role){

        roleService.updateByRole(role);

        return ResponseResult.okResult("修改角色成功");
    }

    @ApiOperation(value = "通过id获取角色信息")
    @GetMapping("/getRoleById/{id}")
    public ResponseResult<Role> getRoleById(@PathVariable("id") Integer id){

        Role role = roleService.getRoleById(id);

        return ResponseResult.okResult(role);
    }

    @ApiOperation(value = "删除角色信息")
    @GetMapping("/deleteRoleById/{id}")
    public ResponseResult<?> deleteRoleById(@PathVariable("id") Integer id){

        roleService.deleteRoleById(id);

        return ResponseResult.okResult("删除角色成功");
    }

    @ApiOperation(value = "获取全部角色信息")
    @GetMapping("/getAllRole")
    public ResponseResult<?> getAllRole(){
        List<Role> roleList = roleService.list();

        return ResponseResult.okResult(roleList);
    }


}
