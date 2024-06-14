package com.meoying.ai.ielts.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class SignupReq {
    @Email(message = "请输入邮箱！")
    private String email;
    @NotEmpty(message = "请输入密码！")
    private String password;
    @NotEmpty(message = "请确认密码！")
    private String confirmPwd;

    public boolean confirmedPwd() {
        return password.equals(confirmPwd);
    }
}
