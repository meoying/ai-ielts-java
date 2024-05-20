package com.meoying.ai.converter;

import com.meoying.ai.dao.entity.UserAccountEntity;
import com.meoying.ai.service.dto.UserAccountDTO;
import com.meoying.ai.service.dto.UserAccountDTO.UserAccountDTOBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-20T15:36:17+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_144 (Oracle Corporation)"
)
public class UserAccountConverterImpl implements UserAccountConverter {

    @Override
    public UserAccountDTO convertToDto(UserAccountEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserAccountDTOBuilder userAccountDTO = UserAccountDTO.builder();

        userAccountDTO.id( entity.getId() );
        userAccountDTO.accountName( entity.getAccountName() );
        userAccountDTO.type( entity.getType() );
        userAccountDTO.password( entity.getPassword() );
        userAccountDTO.createTime( entity.getCreateTime() );
        userAccountDTO.updateTime( entity.getUpdateTime() );

        userAccountDTO.mobile( BaseConvertUtils.convert2Mobile(entity.getMobile()) );

        return userAccountDTO.build();
    }

    @Override
    public UserAccountEntity convertToDo(UserAccountDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserAccountEntity userAccountEntity = new UserAccountEntity();

        userAccountEntity.setId( dto.getId() );
        userAccountEntity.setAccountName( dto.getAccountName() );
        userAccountEntity.setPassword( dto.getPassword() );
        userAccountEntity.setType( dto.getType() );

        userAccountEntity.setMobile( BaseConvertUtils.convert2MobileString(dto.getMobile()) );

        return userAccountEntity;
    }
}
