package com.meoying.ai.ielts.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"}),
        indexes = {@Index(columnList = "mobile", unique = true)})
public class User implements Serializable {
    /**
     * id
     */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long id;
    /**
     * 账号名
     */
    private String email;
    // 昵称
    private String nickname;
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 创建时间
     */
    private Long ctime;
    /**
     * 更新时间
     */
    private Long utime;
}
