package com.meoying.ai.ielts.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@Table(name = "spu",
        indexes = {@Index(columnList = "category0"), @Index(columnList = "category1")},
        uniqueConstraints = @UniqueConstraint(columnNames = {"sn"})
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SpuEntity {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long id;

    // 商品SPU类别0,系统内部使用
    @Column(columnDefinition = "STRING")
    private String category0;

    //商品SPU类别1,系统内部使用
    @Column(columnDefinition = "STRING")
    private String category1;

    //商品SPU序列号
    @Column(columnDefinition = "STRING")
    private String sn;

    //商品名称
    @Column(columnDefinition = "STRING")
    private String name;

    //商品描述
    @Column(columnDefinition = "STRING")
    private String description;

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
