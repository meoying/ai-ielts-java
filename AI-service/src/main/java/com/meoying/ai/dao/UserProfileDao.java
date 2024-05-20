package com.meoying.ai.dao;

import com.meoying.ai.dao.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserProfileDao extends JpaRepository<UserProfileEntity, Long> {

    List<UserProfileEntity> findByUserIdIn(List<Long> userIds);

    UserProfileEntity findByUserId(Long userId);

    List<UserProfileEntity> findByNickname(String nickname);

}
