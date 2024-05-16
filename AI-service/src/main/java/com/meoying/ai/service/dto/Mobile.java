package com.meoying.ai.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mobile implements Serializable {
    private static final long serialVersionUID = 3533058023836270079L;
    private static final String CHINA = "86";
    private static final String MOBILE_DELIMITER = "-";
    private String code;
    private String number;
}
