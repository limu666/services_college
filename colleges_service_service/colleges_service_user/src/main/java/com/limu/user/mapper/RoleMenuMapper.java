package com.limu.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.limu.model.user.pojos.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    public List<Integer> getMenuIdByRoleId(Integer roleId);
}
