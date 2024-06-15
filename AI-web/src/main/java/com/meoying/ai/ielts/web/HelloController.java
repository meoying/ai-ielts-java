package com.meoying.ai.ielts.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public Result hello() {
        return new Result("hello, world");
    }
}
