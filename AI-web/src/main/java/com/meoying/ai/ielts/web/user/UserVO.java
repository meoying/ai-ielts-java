package com.meoying.ai.ielts.web.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVO {
    private String nickname;
    private String avatar;
}
