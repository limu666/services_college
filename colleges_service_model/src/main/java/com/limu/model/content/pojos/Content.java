package com.limu.model.content.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.limu.model.common.xss.JcExcelName;
import com.limu.model.user.pojos.Menu;
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
@TableName("z_content")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Content对象", description = "")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("内容主键")
    @JcExcelName(name = "内容主键")
    private Long contentId;

    @ApiModelProperty("用户id")
    @JcExcelName(name = "用户id")
    private Long userId;

    @ApiModelProperty("类别id")
    @JcExcelName(name = "类别id")
    private Long categoryId;

    @ApiModelProperty("类别id")
    @JcExcelName(name = "内容")
    private String content;

    @ApiModelProperty("状态：0审核1正常2下架3拒绝（审核不通过）")
    @JcExcelName(name = "状态：0审核1正常2下架3拒绝（审核不通过）")
    private Byte status;

    @ApiModelProperty("类型：0文字1图片2视频")
    @JcExcelName(name = "类型：0文字1图片2视频")
    private Byte type;

    @ApiModelProperty("文件数量")
    @JcExcelName(name = "文件数量")
    private Integer fileCount;

    @ApiModelProperty("点赞数量")
    @JcExcelName(name = "点赞数量")
    private Integer loveCount;

    @ApiModelProperty("0不匿名1匿名")
    @JcExcelName(name = "0不匿名1匿名")
    private Byte isAnonymous;

    @ApiModelProperty("备注")
    @JcExcelName(name = "备注")
    private String remark;

    @ApiModelProperty("逻辑删除(1:已删除0:未删除)")
    @TableLogic
    @JcExcelName(name = "逻辑删除(1:已删除0:未删除)")
    private Byte deleted;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @ApiModelProperty("创建时间")
    @JcExcelName(name = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    @JcExcelName(name = "创建人")
    private Long createUser;

    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @JcExcelName(name = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("更新人")
    @JcExcelName(name = "更新人")
    private Long updateUser;

    @TableField(value = "不存在字段：pageNo", exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JcExcelName(name = "pageNo")
    private int pageNo;

    @TableField(value = "不存在字段：pageSize", exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JcExcelName(name = "pageSize")
    private int pageSize;

    @TableField(value = "categoryList", exist = false)
    @JcExcelName(name = "分类列表")
    private List<Category> categoryList;

}
