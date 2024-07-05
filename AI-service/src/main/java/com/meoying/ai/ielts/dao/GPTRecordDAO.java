package com.meoying.ai.ielts.dao;

import com.meoying.ai.ielts.dao.entity.GPTRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GPTRecordDAO extends JpaRepository<GPTRecordEntity, Long> {
    List<GPTRecordEntity> findByUidAndBiz(long userId, String biz);
}
