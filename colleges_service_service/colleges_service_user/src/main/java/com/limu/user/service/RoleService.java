package com.limu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.user.pojos.Role;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author limu
 * @since 2024-01-13
 */
public interface RoleService extends IService<Role> {

    public ResponseResult<?> getRoleList(String roleName, Long pageNo, Long pageSize);

    public boolean existsByRoleName(String roleName);

    public ResponseResult<?> addRole(Role role);

    public Role getRoleById(Integer id);

    public void updateByRole(Role role);

    public void deleteRoleById(Integer id);
}
