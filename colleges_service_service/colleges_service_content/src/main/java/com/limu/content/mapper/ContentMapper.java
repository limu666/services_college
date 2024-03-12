package com.limu.content.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limu.model.content.pojos.Content;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author limu
 * @since 2024-02-21
 */
public interface ContentMapper extends BaseMapper<Content> {
    /**
     * 查询内容
     *
     * @param contentId 内容主键
     * @return 内容
     */
    public Content selectContentByContentId(Long contentId);

    /**
     * 查询内容列表
     *
     * @param content 内容
     * @return 内容集合
     */
    public List<Content> selectContentList(Content content);

    /**
     * 统计
     *
     * @param count 内容
     * @return 结果
     */
    public int getCount();

    /**
     * 修改内容
     *
     * @param content 内容
     * @return 结果
     */
    public int updateContent(Content content);

    /**
     * 删除内容
     *
     * @param contentId 内容主键
     * @return 结果
     */
    public int delContentById(Long contentId);

    /**
     * 批量删除内容
     *
     * @param contentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContentByContentIds(Long[] contentIds);

}
