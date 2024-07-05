package com.meoying.ai.ielts.service.gpt;

import com.meoying.ai.ielts.dao.AccountDAO;
import com.meoying.ai.ielts.dao.entity.AccountEntity;
import jakarta.annotation.Resource;

import java.util.Objects;

// CreditHandler 处理余额有关的事情
public class CreditHandler implements Handler{

    private Handler next;

    // 多少钱 / 1000 tokens
    private long price;

    @Resource
    private AccountDAO accountDAO;
    @Override
    public Response handle(Request req, HandlerContext context) {
        // 1. 验证用户的余额大于 0，但是没办法验证够不够
        AccountEntity accountEntity = accountDAO.findByUid(req.getUid());
        if(Objects.isNull(accountEntity) || accountEntity.getBalance().compareTo(price) < 0){
            throw new RuntimeException("支付校验异常");
        }
        // 执行
        Response resp = next.handle(req, context);

        // 2. 扣减 tokens 对应的余额，也就是 Account 中的 balance
        accountDAO.setBalanceById(accountEntity.getBalance() - price, accountEntity.getBalance(), accountEntity.getId());
        return resp;
    }
}
