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
}
