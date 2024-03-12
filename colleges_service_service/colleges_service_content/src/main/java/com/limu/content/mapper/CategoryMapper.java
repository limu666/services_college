package com.limu.content.mapper;

import com.limu.model.content.pojos.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.limu.model.content.pojos.Content;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author limu
 * @since 2024-02-21
 */
public interface CategoryMapper extends BaseMapper<Category> {


    /**
     * 查询分类列表
     *
     * @param category 分类
     * @return 分类集合
     */
    public List<Category> selectCategoryList(Category category);

    /**
     * 查询分类详细信息
     *
     * @param categoryId 分类
     * @return 分类集合
     */
    public Category selectCategoryByCategoryId(Long categoryId);

    /**
     * 统计
     *
     * @param count 分类
     * @return 结果
     */
    public int getCount();


    /**
     * 新增分类
     *
     * @param category 分类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    public int insertCategory(Category category);


    /**
     * 删除分类
     *
     * @param categoryId 分类主键
     * @return 结果
     */
    public int delCategoryById(Long categoryId);



}
