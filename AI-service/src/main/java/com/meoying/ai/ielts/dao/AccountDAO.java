package com.meoying.ai.ielts.dao;

import com.meoying.ai.ielts.dao.entity.AccountEntity;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByUid(long uid);

    /**
     * 更新余额，delta 为正数，则是增加，delta 为负数，则是减少
     * @param uid
     * @param delta
     * @return
     */
    @Modifying
    @Transactional
    @Query("update AccountEntity en set en.balance = en.balance + ?2 where en.uid = ?1 ")
    int updateBalance(long uid, long delta);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO accounts (uid, balance, ctime, utime) VALUES (:#{#account.uid}, :#{#account.balance}, :#{#account.ctime}, :#{#account.utime}) " +
            "ON DUPLICATE KEY UPDATE balance = balance + VALUES(balance), utime = VALUES(utime)", nativeQuery = true)
    void upsertAccount(@Param("account") AccountEntity account);
}
