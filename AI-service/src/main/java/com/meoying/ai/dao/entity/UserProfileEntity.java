package com.meoying.ai.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "UserProfile_IELTS")
public class UserProfileEntity implements Serializable {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long userId;
    //昵称
    private String nickname;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 性别
     */
    private Integer gender;

    private Integer province;

    private Integer city;

    // 用户来自的渠道
    private String channel;
    /*
     * 用户状态
     */
    private Integer status;

    private Date createTime = new Date();

    private Date updateTime = new Date();

    /** 用户来源类型 */
    private Integer sourceType;
}
