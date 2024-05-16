package com.meoying.ai.converter;

import com.meoying.ai.dao.entity.UserProfileEntity;
import com.meoying.ai.service.dto.UserProfileDTO;
import org.mapstruct.factory.Mappers;

/**
 * @author wangjie29
 * @date 2022/7/19
 */
public interface UserProfileConverter {

    UserProfileConverter INSTANCE = Mappers.getMapper(UserProfileConverter.class);

    UserProfileDTO convertToDto(UserProfileEntity entity);

    UserProfileEntity convertToDo(UserProfileDTO dto);
}
