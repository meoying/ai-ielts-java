package com.meoying.ai.ielts.service.gpt;

import com.meoying.ai.ielts.dao.AccountDAO;
import com.meoying.ai.ielts.dao.entity.AccountEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

// CreditHandler 处理余额有关的事情
@Service
public class CreditHandler extends AbstractHandler{
    @Resource
    private AccountDAO accountDAO;

    @Override
    public Response handle(Request req) {
        // 1. 验证用户的余额大于 0，但是没办法验证够不够
        AccountEntity account = accountDAO.findByUid(req.getUid());
        if(Objects.isNull(account) || account.getBalance().compareTo(0L) <= 0){
            return null;
        }
        // 执行
        Response resp = next.handle(req);
        // 2. 扣减 tokens 对应的余额，也就是 response 中的 amount
        accountDAO.setBalanceById(resp.getAmount(), account.getId());
        return resp;
    }
}
