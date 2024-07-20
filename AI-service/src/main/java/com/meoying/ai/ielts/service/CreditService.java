package com.meoying.ai.ielts.service;

import com.meoying.ai.ielts.dao.AccountDAO;
import com.meoying.ai.ielts.dao.entity.AccountEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CreditService {
    @Resource
    private AccountDAO accountDAO;

    public void addCredit(long uid, long delta) {
        long now = new Date().getTime();
        // 初始化一个
        accountDAO.upsertAccount(AccountEntity.builder()
                .utime(now)
                .uid(uid)
                .balance(delta)
                .ctime(now)
                .build());
    }
}
