package com.meoying.ai.ielts.web.writing;

import com.meoying.ai.ielts.service.IELTsService;
import com.meoying.ai.ielts.service.gpt.CreditInsufficientException;
import com.meoying.ai.ielts.service.gpt.Request;
import com.meoying.ai.ielts.service.gpt.Response;
import com.meoying.ai.ielts.web.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/writing",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class WritingController {
    private static final String biz = "ielts-writing";

    @Resource
    private IELTsService service;

    @PostMapping(path = "/examine", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result examine(@RequestBody @Valid ExamineReq req, HttpSession sess) {
        long uid = (long) sess.getAttribute("uid");
        try {
            IELTsService.WritingAnswer resp = service.checkWriting(uid, req.getQuestion(), req.getWriting());
            return new Result("OK").setData(resp);
        } catch (CreditInsufficientException e) {
            return new Result("insufficient credit").setCode(1);
        }
    }
}
