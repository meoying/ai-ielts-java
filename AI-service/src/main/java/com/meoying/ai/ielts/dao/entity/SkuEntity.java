package com.meoying.ai.ielts.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Table(name = "sku",
        indexes = {@Index(columnList = "uid"), @Index(columnList = "biz,bizId")},
        uniqueConstraints = @UniqueConstraint(columnNames = {"sn"}))
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SkuEntity {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long id;

    //商品SPU序列号
    @Column(columnDefinition = "STRING")
    private String sn;

    // 商品SPU自增ID
    private Long spuId;

    @Column(columnDefinition = "STRING")
    private String name;

    @Column(columnDefinition = "STRING")
    private String description;

    //商品单价;单位为分
    private Long price;

    //库存数量
    private Long stock;

    //库存限制
    private Long stockLimit;

    //销售类型: 1=无限期 2=限时促销 3=预售
    private Integer saleType;

    //商品销售属性,JSON格式
    @Column(columnDefinition = "STRING")
    private String attrs;

    //商品缩略图,CDN绝对路径
    @Column(columnDefinition = "STRING")
    private String image;

    //状态 1=下架 2=上架
    private Integer status;

    /**
     * 创建时间
     */
    @Builder.Default
    private Long ctime = new Date().getTime();
    /**
     * 更新时间
     */
    @Builder.Default
    private Long utime = new Date().getTime();
}
