package com.limu.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author limu
 * @since 2024-01-13
 */
@TableName("z_menu")
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    /**
     * 组成部分
     */
    @TableField("component")
    private String component;

    /**
     * 路径
     */
    @TableField("path")
    private String path;

    /**
     * 重定向地址
     */
    @TableField("redirect")
    private String redirect;

    /**
     * 英文名
     */
    @TableField("name")
    private String name;

    /**
     * 标题名字
     */
    @TableField("title")
    private String title;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 父亲结点是谁
     */
    @TableField("parent_id")
    private Integer parentId;

    /**是否是叶子结点，0不是 1是
     */
    @TableField("is_Leaf")
    private String isLeaf;

    /**
     * 是否隐藏 0未 1是
     */
    @TableField("hidden")
    private Boolean hidden;

    /**
     * 因为用的是mybatis-plus这个字段在表里面是不存在的
     * 所以要屏蔽掉
     */
    @TableField(value = "不存在字段：children", exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Menu> children;

    @TableField(value = "不存在字段：title,icon", exist = false)
    private Map<String, Object> meta;
    public Map<String, Object> getMeta(){
        meta = new HashMap<>();
        meta.put("title", title);
        meta.put("icon", icon);
        return meta;
    }
}
