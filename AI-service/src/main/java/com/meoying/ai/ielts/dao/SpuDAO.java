package com.meoying.ai.ielts.dao;

import com.meoying.ai.ielts.dao.entity.AccountEntity;
import com.meoying.ai.ielts.dao.entity.SpuEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpuDAO {
    SpuEntity findByIdAndStatus(long id, int status);

    SpuEntity findBySnAndStatus(String sn, int status);

    @Modifying
    @Transactional
    @Query("update SpuEntity sp set sp.category0 = ?2, sp.category1 =?3, sp.name =?4, sp.description= ?5 where sp.id = ?1 ")
    int updateSpuBasicInfo(long id, String category0, String category1, String name, String des);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO spu (category0, category1, sn, name, description, status, ctime, utime) VALUES (:#{#spuEntity.category0}, :#{#spuEntity.category1}, :#{#spuEntity.sn}, :#{#spuEntity.name}, :#{#spuEntity.description}, :#{#spuEntity.status}, :#{#spuEntity.ctime}, :#{#spuEntity.utime}) " +
            "ON DUPLICATE KEY UPDATE category0 =VALUES(category0),category1 =VALUES(category1),name =VALUES(name),description =VALUES(description), utime = VALUES(utime)", nativeQuery = true)
    int upsertSpu(@Param("spuEntity") SpuEntity spuEntity);
}
