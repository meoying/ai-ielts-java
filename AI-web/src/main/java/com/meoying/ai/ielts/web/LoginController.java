/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.meoying.ai.ielts.web;

import com.meoying.ai.ielts.domain.User;
import com.meoying.ai.ielts.service.LoginService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 * 存放跟登录注册有关的路由
 */
@RestController
@RequestMapping(value = "/users",
        consumes = "application/json", produces = "application/json")
public class LoginController {

    @Resource
    private LoginService loginService;

    // http://127.0.0.1:8080/hello?name=lisi
    @RequestMapping("/hello")
    public Result hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return new Result("hello, world");
    }

    @PostMapping("/signup")
    public Result signup(@Valid @RequestBody SignupReq req) {
        if(!req.confirmedPwd()) {
            return new Result("两次输入的密码不一致！").setCode(UserBizCode.InvalidSignupParams);
        }
        this.loginService.signup(new User()
                .setEmail(req.getEmail())
                .setPassword(req.getPassword()));
        return new Result("OK");
    }
}
