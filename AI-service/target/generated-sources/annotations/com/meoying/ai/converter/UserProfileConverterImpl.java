package com.meoying.ai.converter;

import com.meoying.ai.dao.entity.UserProfileEntity;
import com.meoying.ai.service.dto.UserProfileDTO;
import com.meoying.ai.service.dto.UserProfileDTO.UserProfileDTOBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T21:01:14+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.23 (Azul Systems, Inc.)"
)
public class UserProfileConverterImpl implements UserProfileConverter {

    @Override
    public UserProfileDTO convertToDto(UserProfileEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserProfileDTOBuilder userProfileDTO = UserProfileDTO.builder();

        userProfileDTO.userId( entity.getUserId() );
        userProfileDTO.nickname( entity.getNickname() );
        userProfileDTO.birthday( entity.getBirthday() );
        userProfileDTO.gender( entity.getGender() );
        userProfileDTO.status( entity.getStatus() );
        userProfileDTO.createTime( entity.getCreateTime() );
        userProfileDTO.updateTime( entity.getUpdateTime() );

        return userProfileDTO.build();
    }

    @Override
    public UserProfileEntity convertToDo(UserProfileDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserProfileEntity userProfileEntity = new UserProfileEntity();

        userProfileEntity.setUserId( dto.getUserId() );
        userProfileEntity.setNickname( dto.getNickname() );
        userProfileEntity.setBirthday( dto.getBirthday() );
        userProfileEntity.setGender( dto.getGender() );
        userProfileEntity.setStatus( dto.getStatus() );

        return userProfileEntity;
    }
}
