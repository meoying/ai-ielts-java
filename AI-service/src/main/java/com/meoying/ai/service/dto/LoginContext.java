package com.meoying.ai.service.dto;

import com.meoying.ai.service.constants.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginContext {
    /**
     * 当前登录账号类型{@link AccountType}
     */
    private Integer type;
    /**
     * 账号信息
     */
    @NotNull
    private UserAccountDTO userAccountDTO;
    /**
     * 个人信息
     */
    @NotNull
    private UserProfileDTO userProfileDTO;
    /**
     * 是否注册
     */
    private Boolean register;

}
