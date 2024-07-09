package com.meoying.ai.ielts.dao;

import com.meoying.ai.ielts.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByUid(long uid);

    @Modifying
    @Query("update AccountEntity en set en.balance = en.balance - ?1 where en.id = ?2 ")
    int setBalanceById(long amt, long id);
}
