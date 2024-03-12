package com.limu.model.user.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO 类描述
 *
 * @author: LiMu
 * @createTime: 2023年07月27日 11:10
 */

@Data
public class AddUserDto {

    /*
    * 用户名
    * */
    @ApiModelProperty(value = "用户名", required = true)
    private String name;

    /*
     * 性别
     * */
    @ApiModelProperty(value = "性别", required = true)
    private Boolean sex;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true)
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态",required = true)
    private Boolean status;

}
