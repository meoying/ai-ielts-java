package com.meoying.ai.converter;

import com.meoying.ai.dao.entity.UserAccountEntity;
import com.meoying.ai.service.dto.UserAccountDTO;
import org.mapstruct.factory.Mappers;

/**
 * @author wangjie29
 * @date 2022/7/19
 */
public interface UserAccountConverter {

    UserAccountConverter INSTANCE = Mappers.getMapper(UserAccountConverter.class);

    UserAccountDTO convertToDto(UserAccountEntity entity);

    UserAccountEntity convertToDo(UserAccountDTO dto);
}
