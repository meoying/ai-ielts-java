package com.meoying.ai.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Long userId;

    private String nickname;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 性别
     */
    private Integer gender;

    // 用户状态
    private Integer status;
    @Column(name="createTime", columnDefinition ="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Date createTime;
    @Column(name="updateTime", columnDefinition ="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false)
    @Generated(GenerationTime.ALWAYS)
    private Date updateTime;
}
