package com.limu.model.user.dtos;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @TableField(value = "不存在字段：verifyCode", exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String verifyCode;
}
