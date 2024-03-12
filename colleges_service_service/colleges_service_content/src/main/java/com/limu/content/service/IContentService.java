package com.limu.content.service;

import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.content.pojos.Content;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author limu
 * @since 2024-02-21
 */
public interface IContentService extends IService<Content> {

    ResponseResult<?> getContentList(Content content);

    ResponseResult<?> getContentById(Long id);

    ResponseResult<?> updateContentById(Content content);

    ResponseResult<?> delContentById(Long contentId);

    ResponseResult<?> exportContentList();
}
