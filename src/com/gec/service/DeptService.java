package com.gec.service;

import com.gec.bean.Dept;
import com.gec.dao.DeptDao;

import java.util.List;


public interface DeptService extends DeptDao {
    Dept findDept(String name);
}
