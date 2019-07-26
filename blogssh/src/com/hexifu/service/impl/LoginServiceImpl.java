package com.hexifu.service.impl;

import com.hexifu.dao.UserDao;
import com.hexifu.domain.User;
import com.hexifu.service.LoginService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LoginServiceImpl implements LoginService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(User user) {
        System.out.println("用户名="+user.getUsername());
        //调用dao查询用户
        User resUser = userDao.getUser(user.getUsername(), user.getPassword());
        return resUser;
    }
}
