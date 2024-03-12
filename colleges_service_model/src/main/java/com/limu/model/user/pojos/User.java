package com.limu.model.user.pojos;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.limu.model.common.xss.JcExcelName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * APP用户信息表
 * </p>
 *
 * @author limu
 */
@Data
@TableName("z_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JcExcelName(name = "主键")
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 密码、通信等加密盐
     */
    @ApiModelProperty("加密盐")
    @JcExcelName(name = "加密盐")
    @TableField("salt")
    private String salt;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @JcExcelName(name = "用户名")
    @TableField("name")
    private String name;

    /**
     * 密码,使用SHA-256进行加密
     */
    @ApiModelProperty("密码")
    @JcExcelName(name = "密码")
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @JcExcelName(name = "手机号")
    @TableField("phone")
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    @JcExcelName(name = "头像")
    @TableField("image")
    private String image;

    /**
     *      1 男
            0 女
     */
    @ApiModelProperty("sex = 1 男 0 女")
    @JcExcelName(name = "1 男 0 女")
    @TableField("sex")
    private Boolean sex;

    /**
     *      是否确认0 未
            1 是
     */
    @ApiModelProperty("是否确认 = 1 是 0 未")
    @JcExcelName(name = "是否确认  0 未  1 是")
    @TableField("is_certification")
    private Boolean certification;

    /**
     * 是否身份认证
     */
    @ApiModelProperty("是否身份认证 = 1 是 0 未")
    @JcExcelName(name = "是否身份认证  0 未  1 是")
    @TableField("is_identity_authentication")
    private Boolean identityAuthentication;

    /**
     * 0正常
       1锁定
     */
    @ApiModelProperty("状态：0正常 1锁定")
    @JcExcelName(name = "状态：0正常 1锁定")
    @TableField("status")
    private Boolean status;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @JcExcelName(name = "备注")
    @TableField("remark")
    private String remark;

    /**
     * 注册时间
     */
    @ApiModelProperty("注册时间")
    @JcExcelName(name = "注册时间")
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;

    /**
     * 是否删除
     *      0 未
     1 是
     */
    @ApiModelProperty("是否删除：  0 未  1 是")
    @JcExcelName(name = "是否删除：  0 未  1 是")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    // 菜单id列表
    @JcExcelName(name = "角色列表")
    @TableField( value = "roleIdList", exist = false)
    private List<Integer> roleIdList;

    @JcExcelName(name = "验证码")
    @TableField(value = "不存在字段：verifyCode", exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String verifyCode;
    public void setCreatedTime() {
        this.createdTime = new Date(); // 将当前时间赋给createdTime属性
    }

}