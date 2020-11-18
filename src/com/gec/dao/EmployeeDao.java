package com.gec.dao;

import com.gec.bean.Employee;
import com.gec.dao.BaseDao;

import java.util.HashMap;
import java.util.List;

public interface EmployeeDao extends BaseDao<Employee> {
    List<Employee> findEmployee(HashMap<String,Object> hm);
    boolean findIds(String id);
}
