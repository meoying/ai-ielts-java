package com.meoying.ai.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO implements Serializable {
    private Long id;
    private String accountName;
    private Long userId;
    private Integer type;
    private Mobile mobile;
    private String extInfo;
    //密码
    private String password;
    @Column(name="createTime", columnDefinition ="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Date createTime;
    @Column(name="updateTime", columnDefinition ="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false)
    @Generated(GenerationTime.ALWAYS)
    private Date updateTime;
}
