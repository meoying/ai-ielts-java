package com.meoying.ai.ielts.dao.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Table(name = "grp_records",
        indexes = {@Index(columnList = "uid", unique = true)})
@Entity
public class GPTRecordEntity implements Serializable {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long id;
    // 用户 ID
    private Long uid;

    // 业务场景
    private String biz;

    //请求
    private String request;

    //结果
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
