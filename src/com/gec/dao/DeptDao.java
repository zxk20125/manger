package com.gec.dao;

import com.gec.bean.Dept;

import java.util.List;

public interface DeptDao extends BaseDao<Dept> {
    Dept findDept(String name);
}
