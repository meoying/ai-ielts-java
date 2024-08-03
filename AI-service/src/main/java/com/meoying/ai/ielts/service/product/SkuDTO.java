package com.meoying.ai.ielts.service.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkuDTO implements Serializable {
    private String sn;

    private String name;

    private String desc;

    private BigDecimal price;

    private Long stock;

    private Integer saleType;

    private Map<String,Object> attrMap;

    private String image;
}
