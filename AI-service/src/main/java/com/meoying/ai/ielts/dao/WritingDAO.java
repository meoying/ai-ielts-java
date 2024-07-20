package com.meoying.ai.ielts.dao;

import com.meoying.ai.ielts.dao.entity.GPTRecordEntity;
import com.meoying.ai.ielts.dao.entity.WritingEntity;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

@Resource
public interface WritingDAO extends JpaRepository<WritingEntity, Long> {

}
