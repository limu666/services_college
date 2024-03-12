package com.limu.user.controller.v1;


import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.common.enums.AppHttpCodeEnum;
import com.limu.model.user.pojos.Menu;
import com.limu.model.user.pojos.Role;
import com.limu.user.service.MenuService;
import com.limu.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
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
@RequestMapping("/api/v1/manage/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @ApiOperation(value = "测试类", tags = {"测试"})
    @GetMapping("/test")
    public void test(){
        log.info("居然失败了。。。。。。");
    }

    /*
    参数

    * */
    @ApiOperation(value = "查询所有菜单数据", tags = {"需要优化"})
    @GetMapping("/getAllMenu")
    public ResponseResult<?> getAllMenu(){

        ResponseResult<?> menuList = menuService.getAllMenu();

        return ResponseResult.okResult(menuList);
    }


}
