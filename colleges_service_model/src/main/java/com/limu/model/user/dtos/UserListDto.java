package com.limu.model.user.dtos;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * TODO 类描述
 *
 * @author: LiMu
 * @createTime: 2023年07月24日 11:08
 */

@Data
public class UserListDto {

    @ApiModelProperty(value = "用户名",required = false)
    private String name;

    @ApiModelProperty(value = "手机号",required = false)
    private String phone;

    @ApiModelProperty(value = "页码",required = true)
    private Long pageNo;

    @ApiModelProperty(value = "页大小",required = true)
    private Long pageSize;

}
