package com.limu.content.service;

import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.content.pojos.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.limu.model.content.pojos.Content;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author limu
 * @since 2024-02-21
 */
public interface ICategoryService extends IService<Category> {

//    public ResponseResult<?> getCategoryList();

    public boolean existsByCategoryName(String roleName);

    ResponseResult<?> getCategoryById(Long id);

    public ResponseResult<?> addCategory(Category category);

    public ResponseResult<?> updateByCategory(Category role);

    public ResponseResult<?> deleteCategoryById(Long id);

    public ResponseResult<Category> getCategoryList();

    public ResponseResult<?> getSelectCategoryList(Category category);
}
