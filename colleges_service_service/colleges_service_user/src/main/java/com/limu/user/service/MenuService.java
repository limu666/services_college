package com.limu.user.service;

import com.limu.model.common.dtos.ResponseResult;
import com.limu.model.user.pojos.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author limu
 * @since 2024-01-13
 */
public interface MenuService extends IService<Menu> {
    public ResponseResult<?> getAllMenu();

    List<Menu> getMenuListByUserId(Integer userId);
}
