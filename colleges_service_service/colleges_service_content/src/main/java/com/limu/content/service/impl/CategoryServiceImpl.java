package com.limu.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.limu.content.mapper.ContentMapper;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.content.pojos.Category;
import com.limu.content.mapper.CategoryMapper;
import com.limu.content.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limu.model.user.pojos.User;
import com.limu.utils.common.DateUtils;
import com.sun.tools.doclets.internal.toolkit.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Resource
    private RedisTemplate<String, Category> redisTemplate;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ContentMapper contentMapper;

    // 获取角色表信息
    @Override
    public ResponseResult<Category> getCategoryList() {

        // 先尝试从 Redis 中获取结果
        List<Category> categoryList = redisTemplate.opsForList().range("categoryList", 0, -1);
        if (categoryList != null && !categoryList.isEmpty()) {
            return ResponseResult.okResult(categoryList);
        }

        // 如果 Redis 中没有结果，则从数据库中查询
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        categoryList = categoryMapper.selectList(wrapper);

        // 将结果保存到 Redis 中
        redisTemplate.opsForList().rightPushAll("categoryList", categoryList.toArray(new Category[0]));

        return ResponseResult.okResult(categoryList);

    }

    @Override
    public ResponseResult<?> getSelectCategoryList(Category category) {
        if(category.getPageNo() == category.getPageSize() || category.getPageNo() > 50 || category.getPageSize() > 50){
            return ResponseResult.errorResult(20001, "访问页数过大，请重新选择");
        }
        // 查询满足条件的内容列表
        List<Category> contentList = categoryMapper.selectCategoryList(category);
        int count = categoryMapper.getCount();


        // 获取分页数据、总记录数并封装到结果中
        Map<String, Object> data = new HashMap<>();
        data.put("total", count);
        data.put("rows", contentList);

        return ResponseResult.okResult(data);
    }

    @Override
    public boolean existsByCategoryName(String categoryName) {
        //1.1查询是否存在分类名字相同的
        Category dbCategory = getOne(Wrappers.<Category>lambdaQuery().eq(Category::getCategoryName, categoryName));
        return true;
    }

    @Override
    public ResponseResult<?> getCategoryById(Long id){
        Category category = categoryMapper.selectCategoryByCategoryId(id);
        if(category == null){
            return ResponseResult.errorResult(20001,"内容查询失败，请重试...");
        }

        return ResponseResult.okResult(category);
    }

    @Override
    public ResponseResult<?> addCategory(Category category) {

        category.setParentId(0L);
        category.setStatus("0");
        category.setDeleted(false);
        category.setCreateUser(8L);
        categoryMapper.insertCategory(category);

        return ResponseResult.okResult("添加成功");
    }

    @Override
    public ResponseResult<?> updateByCategory(Category category) {

        categoryMapper.updateCategory(category);

        redisTemplate.delete("categoryList");

        return ResponseResult.okResult("修改成功");
    }

    @Override
    public ResponseResult<?> deleteCategoryById(Long id) {

        // 查询是否存在id
        Category category = categoryMapper.selectById(id);
        if(category == null){
            return ResponseResult.errorResult(20001,"数据不存在，请刷新重试");
        }

        // 查看是否关联内容
        LambdaQueryWrapper<com.limu.model.content.pojos.Content> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(com.limu.model.content.pojos.Content::getCategoryId,id);
        List<com.limu.model.content.pojos.Content> contentList = contentMapper.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(contentList)){
            return ResponseResult.errorResult(20001,"数据存在关联不可删除！！！");
        }

        // 删除
        int result = categoryMapper.delCategoryById(id);
        if(result > 0){
            return ResponseResult.okResult("删除成功啦……");
        }

        return ResponseResult.errorResult(20001, "居然失败啦……");
    }

}
