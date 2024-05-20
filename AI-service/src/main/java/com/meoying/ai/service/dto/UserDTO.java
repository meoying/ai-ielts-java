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
    private Long userId;
    /**
     * 状态
     */
    private Integer status;

    private UserAccountDTO userAccountDTO;

    private UserProfileDTO userProfileDTO;

}
