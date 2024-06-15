package com.meoying.ai.ielts.dao;

import com.meoying.ai.ielts.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Long> {
    UserEntity getUserByEmail(String email);
}
