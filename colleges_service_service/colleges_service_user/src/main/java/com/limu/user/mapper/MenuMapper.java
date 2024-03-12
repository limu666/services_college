package com.limu.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.limu.model.user.pojos.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    public List<Menu> getMenuListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);
}
