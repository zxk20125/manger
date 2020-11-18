package com.gec.service.impl;

import com.gec.bean.Employee;
import com.gec.bean.PageBean;
import com.gec.dao.EmployeeDao;
import com.gec.dao.impl.EmployeeDaoImp;
import com.gec.service.EmployeeService;

import java.util.HashMap;
import java.util.List;

public class EmployeeServiceImp implements EmployeeService {
    EmployeeDao ed= new EmployeeDaoImp();
    @Override
    public List<Employee> findAll() {
        return ed.findAll();
    }

    @Override
    public Employee findById(int id) {
        return ed.findById(id);
    }

    @Override
    public PageBean<Employee> findPage(int pageNow, Employee entity) {
        return ed.findPage(pageNow,entity);
    }

    @Override
    public boolean save(Employee entity) {
        return ed.save(entity);
    }

    @Override
    public boolean update(Employee entity) {
        return ed.update(entity);
    }

    @Override
    public boolean delete(int id) {
        return ed.delete(id);
    }

    @Override
    public List<Employee> findEmployee(HashMap<String,Object> hm) {
        return ed.findEmployee(hm);
    }

    @Override
    public boolean findIds(String id) {
        return ed.findIds(id);
    }
}
