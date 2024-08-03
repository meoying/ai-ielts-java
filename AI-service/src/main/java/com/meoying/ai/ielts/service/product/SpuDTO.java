package com.meoying.ai.ielts.service.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuDTO implements Serializable {
    private Long id;

    private String sn;

    private String category0;

    private String category1;

    private String name;

    private String desc;
}
