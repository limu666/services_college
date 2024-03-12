package com.limu.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author limu
 * @since 2024-01-13
 */
@TableName("z_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    // 角色名
    @TableField("role_name")
    private String roleName;

    // 角色描述
    @TableField("role_desc")
    private String roleDesc;

    // 菜单id列表
    @TableField( value = "菜单id列表", exist = false)
    private List<Integer> menuIdList;


}
