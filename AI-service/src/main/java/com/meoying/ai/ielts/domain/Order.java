package com.meoying.ai.ielts.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private String payNo;
    private BigDecimal transAmount;
    private String openId;
    private String ip;
    private String payNotifyUrl;
}
