package com.meoying.ai.ielts.dao.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
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
    private Long ctime;
    /**
     * 更新时间
     */
    private Long utime;
}
