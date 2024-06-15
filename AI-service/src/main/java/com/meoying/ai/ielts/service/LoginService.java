package com.meoying.ai.ielts.service;

import com.meoying.ai.ielts.dao.entity.UserEntity;
import com.meoying.ai.ielts.dao.UserDAO;
import com.meoying.ai.ielts.domain.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class LoginService {
    @Resource
    private UserDAO userDAO;

    public void signup(User user) {
        userDAO.save(new UserEntity()
                        .setPassword(this.encryptPwd(user.getPassword()))
                .setEmail(user.getEmail()));
    }

    private String encryptPwd(String pwd) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return  passwordEncoder.encode(pwd);
    }

    private boolean comparePwd(String input, String pwd) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(input, pwd);
    }

    /**
     *
     * @param email 邮箱
     * @param password 密码
     * @return null 则代表登录失败
     */
    public User login(String email, String password) {
        UserEntity u = this.userDAO.getUserByEmail(email);
        if(this.comparePwd(password, u.getPassword())) {
            // 登录成功
            return new User().setId(u.getId());
        }
        return null;
    }

    public User profile(long uid) {
        UserEntity u = this.userDAO.getReferenceById(uid);
        return new User().setId(u.getId())
                .setNickname(u.getNickname())
                .setAvatar(u.getAvatar());
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
