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
@TableName("z_content_love")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ContentLove对象", description = "")
public class ContentLove implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("文章id")
    private Long contentId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
