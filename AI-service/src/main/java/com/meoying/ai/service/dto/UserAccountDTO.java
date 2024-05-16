package com.meoying.ai.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO implements Serializable {
    private Long id;
    private String accountName;
    private Integer type;
    private Mobile mobile;
    private String extInfo;
    //密码
    private String password;
    private Date createTime;
    private Date updateTime;
}
