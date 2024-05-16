package com.meoying.ai.service;

import com.meoying.ai.converter.UserAccountConverter;
import com.meoying.ai.converter.UserProfileConverter;
import com.meoying.ai.dao.UserAccountDao;
import com.meoying.ai.dao.UserProfileDao;
import com.meoying.ai.dao.entity.UserAccountEntity;
import com.meoying.ai.dao.entity.UserProfileEntity;
import com.meoying.ai.service.dto.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class CommonProxyService {
    @Resource
    private UserAccountDao userAccountDao;

    @Resource
    private UserProfileDao userProfileDao;

    @Transactional
    public void register(LoginContext loginContext){
        UserProfileEntity profile = userProfileDao.save(UserProfileConverter.INSTANCE.convertToDo(loginContext.getUserProfileDTO()));
        UserAccountEntity entity = UserAccountConverter.INSTANCE.convertToDo(loginContext.getUserAccountDTO());
        entity.setUserId(profile.getUserId());
        userAccountDao.save(entity);

    }

}
