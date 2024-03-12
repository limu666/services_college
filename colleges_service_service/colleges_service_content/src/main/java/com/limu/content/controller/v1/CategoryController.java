package com.limu.content.controller.v1;

import com.limu.content.service.ICategoryService;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.common.enums.AppHttpCodeEnum;
import com.limu.model.content.pojos.Category;
import com.limu.model.content.pojos.Content;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author limu
 * @since 2024-02-21
 */
@Api(value = "内容分类管理", tags = "后台系统内容管理")
@RestController  //默认做JSON处理
@Slf4j
@RequestMapping("/api/v1/manage/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @ApiOperation(value = "测试类", tags = {"测试"})
    @GetMapping("/test")
    public void test(){
        log.info("居然失败了。。。。。。");
    }

    /*
    @param category

    * */
    @ApiOperation(value = "查询分类或按条件查询", tags = {"需要优化"})
    @GetMapping("/getSelectCategoryList")
    public ResponseResult<?> getSelectCategoryList(Category category){

        return categoryService.getSelectCategoryList(category);
    }

    @ApiOperation(value = "新增分类")
    @PostMapping("/addCategory")
    public ResponseResult<?> addCategory(@RequestBody Category category){
        try{
            if(category == null){
                //验证用户输入是否为空
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
            // 对用户输入进行过滤和转义，防止跨站点脚本攻击
            category.setCategoryName(HtmlUtils.htmlEscape(category.getCategoryName()));
            category.setDescription(HtmlUtils.htmlEscape(category.getDescription()));

            // 强化密码，确保密码的安全性，包括密码的复杂性和加密存储
            //role.setPassword(passwordEncoder.encode(role.getPassword()));

            // 防止重复提交
            if(!categoryService.existsByCategoryName(category.getCategoryName())) { // 查询数据库是否存在相同的分类名称
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
            }

            categoryService.addCategory(category);

            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

    }

    @ApiOperation(value = "修改分类信息")
    @PutMapping("/updateCategoryById")
    public ResponseResult<?> updateCategory(@RequestBody Category category){

        if(!categoryService.existsByCategoryName(category.getCategoryName())){
            return ResponseResult.errorResult(20001,"分类名重复");
        }

        return categoryService.updateByCategory(category);
    }

    @ApiOperation(value = "通过id获取分类信息")
    @GetMapping("/getCategoryById/{id}")
    public ResponseResult<?> getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("/deleteCategoryById/{id}")
    public ResponseResult<?> deleteCategoryById(@PathVariable("id") Long id){

        return categoryService.deleteCategoryById(id);
    }

    @ApiOperation(value = "获取全部分类信息")
    @GetMapping("/getCategoryList")
    public ResponseResult<?> getCategoryList(){

        return categoryService.getCategoryList();

    }
}
