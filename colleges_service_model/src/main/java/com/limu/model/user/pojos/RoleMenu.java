package com.limu.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author limu
 * @since 2024-01-13
 */
@TableName("z_role_menu")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 角色id
    @TableField("role_id")
    private Integer roleId;

    // 菜单id
    @TableField("menu_id")
    private Integer menuId;

}
