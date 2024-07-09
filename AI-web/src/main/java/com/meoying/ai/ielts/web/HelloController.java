package com.meoying.ai.ielts.web;

import com.meoying.ai.ielts.service.gpt.Request;
import com.meoying.ai.ielts.service.gpt.Response;
import com.meoying.ai.ielts.service.gpt.ZhipuHandler;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
    @RequestMapping("/hello")
    public Result hello() {
        return new Result("hello, world");
    }

    @Resource
    private ZhipuHandler zhipuHandler;
    @GetMapping("/test/zhipu")
    public Response test(@RequestParam(value = "prompt") String prompt){
        return zhipuHandler.handle(Request.builder()
                        .prompt(prompt).build());
    }
}
