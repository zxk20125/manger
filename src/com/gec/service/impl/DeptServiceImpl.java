package com.gec.service.impl;

import com.gec.bean.Dept;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.DeptDao;
import com.gec.dao.UserDao;
import com.gec.dao.impl.DeptDaoImp;
import com.gec.dao.impl.UserDaoImpl;
import com.gec.service.DeptService;
import com.gec.service.UserService;

import java.util.List;

public class DeptServiceImpl implements DeptService {
	DeptDao dd=new DeptDaoImp();

	@Override
	public List<Dept> findAll() {
		return dd.findAll();
	}

	@Override
	public Dept findById(int id) {
		return dd.findById(id);
	}

	@Override
	public PageBean<Dept> findPage(int pageNow, Dept entity) {
		return dd.findPage(pageNow,entity);
	}

	@Override
	public boolean save(Dept entity) {
		return dd.save(entity);
	}

	@Override
	public boolean update(Dept entity) {
		return dd.update(entity);
	}

	@Override
	public boolean delete(int id) {
		return dd.delete(id);
	}

	@Override
	public Dept findDept(String name) {
		return dd.findDept(name);
	}
}
