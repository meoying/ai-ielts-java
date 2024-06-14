package com.meoying.ai.ielts.service;

import com.meoying.ai.ielts.dao.entity.User;
import com.meoying.ai.ielts.dao.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class LoginService {
    @Resource
    private UserDAO userAccountDao;

    public void accountSignup(com.meoying.ai.ielts.domain.User user) {
        userAccountDao.save(new User()
                        .setPassword(user.getPassword())
                .setEmail(user.getEmail()));
    }
//    public boolean register(LoginContext loginContext){
//        if(Objects.isNull(loginContext) || Objects.isNull(loginContext.getRegister()) || Boolean.FALSE.equals(loginContext.getRegister())){
//            throw new IgnoredException(400,"参数异常");
//        }
//        if(!ValidateUtil.validate(loginContext)){
//            throw new IgnoredException(400,"参数异常");
//        }
//        UserAccountDTO userAccountDTO = loginContext.getUserAccountDTO();
//        try {
//            UserAccountEntity userAccountEntity = userAccountDao.findByAccountName(userAccountDTO.getAccountName());
//            if(Objects.isNull(userAccountEntity)){
//                commonProxyService.register(loginContext);
//            }else {
//                UserProfileEntity profileExist = userProfileDao.findByUserId(userAccountDTO.getUserId());
//                UserProfileEntity newProfile = UserProfileConverter.INSTANCE.convertToDo(loginContext.getUserProfileDTO());
//                if(Objects.nonNull(profileExist)){
//                    newProfile.setUserId(profileExist.getUserId());
//                }
//                userProfileDao.save(newProfile);
//            }
//            return true;
//        }catch (Exception e){
//           log.warn("register fail!loginContext {}", JsonUtils.toJson(loginContext), e);
//        }
//        return false;
//    }
//
//    /**
//     * 登录
//     * @param loginContext
//     * @return
//     */
//    public UserDTO login(LoginContext loginContext){
//        UserAccountDTO userAccountDTO = loginContext.getUserAccountDTO();
//        if(loginContext.getType().equals(AccountType.PASS)){
//            UserAccountEntity userAccountEntity = userAccountDao.findByAccountName(userAccountDTO.getAccountName());
//            if(Objects.nonNull(userAccountEntity) && userAccountDTO.getPassword().equals(userAccountEntity.getPassword())){
//                UserDTO userDTO = UserDTO.builder()
//                        .userAccountDTO(UserAccountConverter.INSTANCE.convertToDto(userAccountEntity))
//                        .userId(userAccountEntity.getUserId())
//                        .build();
//                UserProfileEntity profileExist = userProfileDao.findByUserId(userAccountEntity.getUserId());
//                userDTO.setUserProfileDTO(UserProfileConverter.INSTANCE.convertToDto(profileExist));
//                return userDTO;
//            }
//        }
//        return null;
//    }
}
