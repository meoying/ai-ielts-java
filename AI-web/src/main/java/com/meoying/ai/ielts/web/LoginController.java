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
import com.meoying.ai.ielts.utils.ValidateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    @ResponseBody
    public Result hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return new Result("hello, world");
    }

    @RequestMapping("/signup")
    @ResponseBody
    public Result accountSignup(@RequestBody SignupReq req) {
        if(!ValidateUtil.validate(req)) {
            return new Result("参数错误").setCode(UserBizCode.InvalidSignupParams);
        }
        this.loginService.accountSignup(new User()
                .setEmail(req.getEmail())
                .setPassword(req.getPassword()));
        return new Result("OK");
    }
}
