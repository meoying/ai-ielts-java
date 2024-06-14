package com.meoying.ai.ielts.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private String email;
    private String password;
}
