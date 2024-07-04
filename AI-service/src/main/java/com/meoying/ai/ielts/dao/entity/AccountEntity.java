package com.meoying.ai.ielts.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "accounts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"uid"}))
public class AccountEntity implements Serializable {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long id;
    // 余额，分，不需要考虑国际货币的问题
    private Long balance;

    // 用户 ID
    private Long uid;

    /**
     * 创建时间
     */
    private Long ctime;
    /**
     * 更新时间
     */
    private Long utime;
}
