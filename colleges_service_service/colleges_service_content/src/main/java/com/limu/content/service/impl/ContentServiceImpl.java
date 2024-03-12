package com.limu.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limu.file.ExportCsv;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.content.pojos.Category;
import com.limu.model.content.pojos.Content;
import com.limu.content.mapper.ContentMapper;
import com.limu.content.service.IContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limu.model.user.pojos.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author limu
 * @since 2024-02-21
 */
@Service
@Transactional
@Slf4j
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements IContentService {

    @Resource
    private ContentMapper contentMapper;

    @Override
    public ResponseResult<?> getContentList(Content content) {
        if(content.getPageNo() == content.getPageSize() || content.getPageNo() > 50 || content.getPageSize() > 50){
            return ResponseResult.errorResult(20001, "访问页数过大，请重新选择");
        }
        // 查询满足条件的内容列表
        List<Content> contentList = contentMapper.selectContentList(content);
        int count = contentMapper.getCount();


        // 获取分页数据、总记录数并封装到结果中
        Map<String, Object> data = new HashMap<>();
        data.put("total", count);
        data.put("rows", contentList);

        return ResponseResult.okResult(data);
    }

    @Override
    public ResponseResult<Content> getContentById(Long id) {

        Content content = contentMapper.selectContentByContentId(id);
        if(content == null){
            return ResponseResult.errorResult(20001,"内容查询失败，请重试...");
        }

        return ResponseResult.okResult(content);

    }

    @Override
    public ResponseResult<?> updateContentById(Content content) {

        if(content.getContent() != null ){
            content.setContent(HtmlUtils.htmlEscape(content.getContent()));
        }
        if(content.getRemark() != null){
            content.setRemark(HtmlUtils.htmlEscape(content.getRemark()));
        }
        contentMapper.updateContent(content);

        return ResponseResult.okResult("更新成功");
    }

    /**
     * 删除内容信息
     *
     * @param contentId 内容主键
     * @return 结果
     */
    @Override
    public ResponseResult<?> delContentById(Long contentId) {


        ResponseResult<?> result = getContentById(contentId);

        if(result.getCode() != 20000){
            ResponseResult.errorResult(20001,"未查询到ID请重试");
        }

        UpdateWrapper<Content> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("content_id", contentId);
        this.remove(deleteWrapper);

        return ResponseResult.okResult("删除成功...");
    }

    @Override
    public ResponseResult<?> exportContentList() {

        LambdaQueryWrapper<Content> wrapper = new LambdaQueryWrapper<>();

        List<Content> contentList = this.baseMapper.selectList(wrapper);

        String fileName = ExportCsv.exportCsv(contentList);

        return ResponseResult.okResult("导出成功！！！目录地址为：" + fileName);
    }

}
