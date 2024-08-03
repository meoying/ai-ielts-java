package com.meoying.ai.ielts.dao;

import com.meoying.ai.ielts.dao.entity.SkuEntity;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuDAO {

    SkuEntity findBySnAndStatus(String sn, int status);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO sku (sn, spuId, name, description, price, stock, stockLimit, saleType, attrs, image, status, ctime, utime) VALUES (:#{#skuEntity.sn}, :#{#skuEntity.spuId}, :#{#skuEntity.name}, :#{#skuEntity.description}, :#{#skuEntity.price}, :#{#skuEntity.stock}, :#{#skuEntity.stockLimit}, :#{#skuEntity.saleType}, :#{#skuEntity.attrs}, :#{#skuEntity.image}, :#{#skuEntity.status}, :#{#skuEntity.ctime}, :#{#skuEntity.utime}) " +
            "ON DUPLICATE KEY UPDATE name =VALUES(name),description =VALUES(description),price =VALUES(price),stock =VALUES(stock),stockLimit =VALUES(stockLimit),saleType =VALUES(saleType),attrs =VALUES(attrs),image =VALUES(image), utime = VALUES(utime)", nativeQuery = true)
    void upsertSku(@Param("skuEntity")SkuEntity skuEntity);
}
