package com.limu.model.content.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("z_content_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ContentTag对象", description = "")
public class ContentTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("内容id")
    private Long contentId;

    @ApiModelProperty("标签id")
    private Long tagId;

}
