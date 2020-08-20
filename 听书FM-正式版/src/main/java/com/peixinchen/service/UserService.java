package com.peixinchen.service;

import com.peixinchen.dao.UserDao;
import com.peixinchen.model.User;

import java.sql.SQLException;

/*
提供偏向业务角度的方法
 */
public class UserService {
    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public User register(String username, String password) throws SQLException {
        return userDao.insert(username, password);
    }

    public User login(String username, String password) throws SQLException {
        return userDao.select(username, password);
    }
}
