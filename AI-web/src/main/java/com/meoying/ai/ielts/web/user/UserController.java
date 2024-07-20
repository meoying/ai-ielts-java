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

package com.meoying.ai.ielts.web.user;

import com.meoying.ai.ielts.domain.User;
import com.meoying.ai.ielts.service.LoginService;
import com.meoying.ai.ielts.web.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * 存放跟登录注册有关的路由
 */
@RestController
@RequestMapping(path = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final static String uidKey = "uid";

    @Resource
    private LoginService loginService;

    @PostMapping(path = "/signup",
            consumes =  MediaType.APPLICATION_JSON_VALUE)
    public Result signup(@Valid @RequestBody SignupReq req) {
        if(!req.confirmedPwd()) {
            return new Result("两次输入的密码不一致！").setCode(UserBizCode.InvalidSignupParams);
        }
        this.loginService.signup(new User()
                .setEmail(req.getEmail())
                .setPassword(req.getPassword()));
        return new Result("OK");
    }

    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result login(@Valid @RequestBody LoginReq req, HttpServletRequest httpReq) {
        User u = this.loginService.login(req.getEmail(), req.getPassword());
        if(u == null) {
            return new Result("邮箱或者密码错误！").setCode(1);
        }

        // 登录成功，设置 session
        HttpSession sess = httpReq.getSession(true);
        sess.setAttribute(uidKey, u.getId());
        // 返回用户的基本信息
        return new Result("OK").setData(new UserVO()
                .setNickname(u.getNickname()).setAvatar(u.getAvatar()));
    }

    @GetMapping(value = "/profile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Result profile(HttpSession sess) {
        long uid = (long) sess.getAttribute(uidKey);
        User u = this.loginService.profile(uid);
        return new Result().setData(new UserVO()
                .setNickname(u.getNickname()).setAvatar(u.getAvatar()));
    }

//    为了便于你调试引入的一个调试功能
}
