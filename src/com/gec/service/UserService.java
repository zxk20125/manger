package com.gec.service;

import com.gec.bean.User;
import com.gec.dao.UserDao;

import java.util.List;

public interface UserService extends UserDao {
    List<User> findUser(String loginname,String status);
}
