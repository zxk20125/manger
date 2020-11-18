package com.gec.service;

import com.gec.bean.Employee;
import com.gec.dao.EmployeeDao;

import java.util.HashMap;
import java.util.List;


public interface EmployeeService extends EmployeeDao {
    List<Employee> findEmployee(HashMap<String,Object> hm);
    boolean findIds(String id);
}
