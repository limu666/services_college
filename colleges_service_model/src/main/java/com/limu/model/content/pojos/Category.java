package com.limu.model.content.pojos;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author limu
 * @since 2024-02-21
 */
@TableName("z_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Category对象", description = "")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类主键")
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;

    @ApiModelProperty("分类名")
    private String categoryName;

    @ApiModelProperty("父分类id")
    private Long parentId;

    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    @ApiModelProperty("缩略名")
    private String slug;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("系统内置（Y是 N否）")
    private String type;

    @ApiModelProperty("分类图标 ")
    private String icon;

    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("逻辑删除(1:已删除，0:未删除)")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime updateTime;

    @ApiModelProperty("更新人")
    private Long updateUser;

    @TableField(value = "不存在字段：pageNo", exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int pageNo;

    @TableField(value = "不存在字段：pageSize", exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int pageSize;
    /** 内容信息 */
    @TableField(value = "contentList", exist = false)
    private List<Content> contentList;

}
