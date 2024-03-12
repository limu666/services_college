package com.limu.content.controller.v1;

import com.limu.content.service.IContentService;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.common.enums.AppHttpCodeEnum;
import com.limu.model.content.pojos.Content;
import com.limu.model.user.pojos.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author limu
 * @since 2024-02-21
 */

@Api(value = "文章内容管理", tags = "文章内容管理")
@Slf4j
@RestController  //默认做JSON处理
@RequestMapping("/api/v1/manage/content")
public class ContentController {

    @Resource
    private IContentService contentService;

    @ApiOperation(value = "测试类", tags = {"测试"})
    @GetMapping("/test")
    public void test(){
        log.info("居然失败了。。。。。。");
    }

    @ApiOperation(value = "查询文章内容", tags = {"需要优化"})
    @GetMapping("/list")
    public ResponseResult<?> getContentList(Content content){

        return contentService.getContentList(content);
    }

    @ApiOperation(value = "通过id获取内容信息")
    @GetMapping("/getContentById/{id}")
    public ResponseResult<?> getContentById(@PathVariable("id") Long id){

        return contentService.getContentById(id);
    }

    @ApiOperation(value = "修改内容")
    @PutMapping("/updateContentById")
    public ResponseResult<?> updateContentById(@RequestBody Content content){

        try{
            if(content == null){
                // 防止空数据
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        return contentService.updateContentById(content);
    }

    @ApiOperation(value = "删除内容（逻辑删除）")
    @DeleteMapping("/delContentById/{contentId}")
    public ResponseResult<?> delContentById(@PathVariable("contentId") Long contentId){

        return contentService.delContentById(contentId);

    }

    @ApiOperation(value = "导出表格")
    @GetMapping("/export")
    public ResponseResult<?> export(){

        return contentService.exportContentList();

    }

}
