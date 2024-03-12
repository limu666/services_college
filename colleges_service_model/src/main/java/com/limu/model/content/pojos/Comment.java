package com.limu.model.content.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("z_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论主键")
    private Long commentId;

    @ApiModelProperty("上级id")
    private Long parentId;

    @ApiModelProperty("所属的一级评论id")
    private Long oneLevelId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("所回复目标评论的用户id")
    private Long toUserId;

    @ApiModelProperty("内容id")
    private Long contentId;

    @ApiModelProperty("评论内容")
    private String coContent;

    @ApiModelProperty("评论时的ip")
    private String ip;

    @ApiModelProperty("评论时的地址")
    private String address;

    @ApiModelProperty("逻辑删除(1:已删除，0:未删除)")
    private Boolean delFlag;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("更新人")
    private Long updateUser;


}
