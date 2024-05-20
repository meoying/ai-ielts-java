package com.meoying.ai.converter;

import com.meoying.ai.dao.entity.UserAccountEntity;
import com.meoying.ai.service.dto.UserAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author wangjie29
 * @date 2022/7/19
 */
@Mapper
public interface UserAccountConverter {

    UserAccountConverter INSTANCE = Mappers.getMapper(UserAccountConverter.class);
    @Mappings({
            @Mapping(target = "mobile", expression = "java(BaseConvertUtils.convert2Mobile(entity.getMobile()))")
    })
    UserAccountDTO convertToDto(UserAccountEntity entity);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "mobile", expression = "java(BaseConvertUtils.convert2MobileString(dto.getMobile()))")
    UserAccountEntity convertToDo(UserAccountDTO dto);
}
