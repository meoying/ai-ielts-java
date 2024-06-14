package com.meoying.ai.ielts.web;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SignupReq {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String confirmPwd;

    public boolean confirmedPwd() {
        return password.equals(confirmPwd);
    }
}
