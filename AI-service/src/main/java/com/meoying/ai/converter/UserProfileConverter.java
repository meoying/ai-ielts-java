package com.meoying.ai.converter;

import com.meoying.ai.dao.entity.UserProfileEntity;
import com.meoying.ai.service.dto.UserProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author wangjie29
 * @date 2022/7/19
 */
@Mapper
public interface UserProfileConverter {

    UserProfileConverter INSTANCE = Mappers.getMapper(UserProfileConverter.class);

    UserProfileDTO convertToDto(UserProfileEntity entity);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    UserProfileEntity convertToDo(UserProfileDTO dto);
}
