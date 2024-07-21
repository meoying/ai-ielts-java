package com.meoying.ai.ielts.web.mock;

import com.meoying.ai.ielts.service.CreditService;
import com.meoying.ai.ielts.web.AbstractController;
import com.meoying.ai.ielts.web.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// MockController 主要是为了给你提供一些便利功能，方便你调试和查看
@RestController
@RequestMapping(path = "/mock")
public class MockController extends AbstractController {

    @Resource
    private CreditService creditSvc;

    /**
     * 给自己增加积分，每次调用增加 10000 积分。
     * @param sess
     * @return
     */
    @GetMapping(path = "/credit/add")
    public Result addCredit(HttpSession sess) {
        long uid = this.getUid(sess);
        this.creditSvc.addCredit(uid, 10000);
        return new Result("OK");
    }
}
