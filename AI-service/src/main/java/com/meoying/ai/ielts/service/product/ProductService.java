package com.meoying.ai.ielts.service.product;

import com.meoying.ai.ielts.dao.SkuDAO;
import com.meoying.ai.ielts.dao.SpuDAO;
import com.meoying.ai.ielts.dao.entity.SpuEntity;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductService {
    @Resource
    private SkuDAO skuDAO;

    @Resource
    private SpuDAO spuDAO;

    public boolean saveSpu(SpuDTO spuDTO){
        SpuEntity spuEntity= SpuEntity.builder().
                id(spuDTO.getId()).
                category0(spuDTO.getCategory0()).
                category1(spuDTO.getCategory1()).
                name(spuDTO.getName()).
                description(spuDTO.getDesc()).
                build();
        return spuDAO.upsertSpu(spuEntity) > 0;
    }

    public SpuDTO queryBySn(String sn, int status){
        SpuEntity spuEntity =spuDAO.findBySnAndStatus(sn, status);
        if(Objects.isNull(spuEntity)){
            return null;
        }
        SpuDTO spuDTO = new SpuDTO();
        BeanUtils.copyProperties(spuEntity, spuDTO);
        return spuDTO;
    }
}
