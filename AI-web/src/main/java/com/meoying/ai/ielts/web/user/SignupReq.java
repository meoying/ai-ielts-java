package com.meoying.ai.ielts.web.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupReq implements Serializable {
    @Email(message = "请输入邮箱！")
    private String email;
    @NotEmpty(message = "请输入密码！")
    private String password;
    @NotEmpty(message = "请确认密码！")
    private String confirmPwd;

    private Boolean accepted;

    public boolean confirmedPwd() {
        return password.equals(confirmPwd);
    }
}
