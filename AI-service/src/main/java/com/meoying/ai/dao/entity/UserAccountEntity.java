package com.meoying.ai.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "UserAccount_IELTS", uniqueConstraints = @UniqueConstraint(columnNames = {"accountName"}),
indexes = {@Index(columnList = "mobile")})
public class UserAccountEntity implements Serializable {
    /**
     * id
     */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long id;
    /**
     * 账号名
     */
    private String accountName;
    /**
     * userId
     */
    private Long userId;
    /**
     * 密码
     */
    private String password;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
