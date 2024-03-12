package com.limu.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * APP用户粉丝信息表
 * </p>
 *
 * @author limu
 * @since 2024-01-13
 */
@TableName("z_user_fan")
public class UserFan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 粉丝ID
     */
    private Integer fansId;

    /**
     * 粉丝昵称
     */
    private String fansName;

    /**
     * 粉丝忠实度	            0 正常	            1 潜力股	            2 勇士	            3 铁杆	            4 老铁
     */
    private Boolean level;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 是否可见我动态 0否 1是
     */
    private Boolean isDisplay;

    /**
     * 是否屏蔽私信 0否 1是
     */
    private Boolean isShieldLetter;

    /**
     * 是否屏蔽评论 0否 1是
     */
    private Boolean isShieldComment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFansId() {
        return fansId;
    }

    public void setFansId(Integer fansId) {
        this.fansId = fansId;
    }

    public String getFansName() {
        return fansName;
    }

    public void setFansName(String fansName) {
        this.fansName = fansName;
    }

    public Boolean getLevel() {
        return level;
    }

    public void setLevel(Boolean level) {
        this.level = level;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Boolean getIsShieldLetter() {
        return isShieldLetter;
    }

    public void setIsShieldLetter(Boolean isShieldLetter) {
        this.isShieldLetter = isShieldLetter;
    }

    public Boolean getIsShieldComment() {
        return isShieldComment;
    }

    public void setIsShieldComment(Boolean isShieldComment) {
        this.isShieldComment = isShieldComment;
    }

    @Override
    public String toString() {
        return "UserFan{" +
            "id = " + id +
            ", userId = " + userId +
            ", fansId = " + fansId +
            ", fansName = " + fansName +
            ", level = " + level +
            ", createdTime = " + createdTime +
            ", isDisplay = " + isDisplay +
            ", isShieldLetter = " + isShieldLetter +
            ", isShieldComment = " + isShieldComment +
        "}";
    }
}
