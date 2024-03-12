package com.limu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.common.enums.AppHttpCodeEnum;
import com.limu.model.user.pojos.Role;
import com.limu.model.user.pojos.RoleMenu;
import com.limu.user.mapper.RoleMapper;
import com.limu.user.mapper.RoleMenuMapper;
import com.limu.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 类描述
 *
 * @author: LiMu
 * @createTime: 2024年01月13日 18:17
 */
@Service
@Transactional
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RedisTemplate<String, Role> redisTemplate;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    // 获取角色表信息
    @Override
    public ResponseResult<?> getRoleList(String roleName, Long pageNo, Long pageSize) {

        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(org.springframework.util.StringUtils.hasLength(roleName), Role::getRoleName, roleName);

        if(roleName.length() != 0){
            pageNo = 1L;
        }

        Page<Role> page = new Page<>(pageNo, pageSize);
        this.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return ResponseResult.okResult(data);

    }

    // 验证是否是唯一手机号
    @Override
    public boolean existsByRoleName(String roleName) {
        //1.1根据手机号查询用户
        Role dbRole = getOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName, roleName));
        if(dbRole == null){
            return true;
        }
        return false;
    }

    @Override
    public ResponseResult<?> addRole(Role role) {
        // 写入角色表
        this.baseMapper.insert(role);
        // 写入菜单角色关系表
        if(null != role.getMenuIdList()){
            for (Integer menuId : role.getMenuIdList()) {
                roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(), menuId));
            }
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public Role getRoleById(Integer id) {

        Role role = this.baseMapper.selectById(id);
        List<Integer> menuIdList = roleMenuMapper.getMenuIdByRoleId(id);
        role.setMenuIdList(menuIdList);

        return role;
    }

    @Override
    public void updateByRole(Role role) {
        // 修改角色表
        this.baseMapper.updateById(role);
        //修改原有权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, role.getRoleId());
        roleMenuMapper.delete(wrapper);
        // 新增权限
        if(null != role.getMenuIdList()){
            for (Integer menuId : role.getMenuIdList()) {
                roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(), menuId));
            }
        }
    }

    @Override
    public void deleteRoleById(Integer id) {
        this.baseMapper.deleteById(id);
        //修改原有权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, id);
        roleMenuMapper.delete(wrapper);
    }
}
