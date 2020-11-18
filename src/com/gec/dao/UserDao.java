package com.gec.dao;

import com.gec.bean.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

	User login(String username,String password);
	List<User> findUser(String username,String status);
}
