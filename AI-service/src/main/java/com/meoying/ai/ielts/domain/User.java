package com.meoying.ai.ielts.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private long id;
    private String email;
    private String password;
    private String nickname;
    private String avatar;
}
