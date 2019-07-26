package com.hexifu.dao;

import com.hexifu.domain.User;

public interface UserDao {
    public User getUser(String username,String password);
}
