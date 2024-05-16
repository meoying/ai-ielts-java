package com.meoying.ai.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录关注的基本信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否有手机
     */
    private Boolean hasMobile;

    /**
     * 是否设置密码
     */
    private Boolean hasPassword;

    private String nickname;

}
