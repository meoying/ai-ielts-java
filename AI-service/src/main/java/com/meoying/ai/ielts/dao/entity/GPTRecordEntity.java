package com.meoying.ai.ielts.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Table(name = "grp_records",
        indexes = {@Index(columnList = "uid"), @Index(columnList = "biz,bizId")})
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GPTRecordEntity implements Serializable {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long id;
    // 用户 ID
    private Long uid;

    // 业务场景
    private String biz;

    private String bizId;

    //请求
    @Column(columnDefinition = "TEXT")
    private String request;

    //结果
    @Column(columnDefinition = "TEXT")
    private String response;

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
