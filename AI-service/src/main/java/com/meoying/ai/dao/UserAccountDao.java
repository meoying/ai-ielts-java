package com.meoying.ai.dao;

import com.meoying.ai.dao.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserAccountDao extends JpaRepository<UserAccountEntity, Long> {

    List<UserAccountEntity> findByMobileIn(List<String> mobiles);

    List<UserAccountEntity> findByAccountNameIn(List<String> accountNames);

    UserAccountEntity findByAccountName(String accountName);
}
