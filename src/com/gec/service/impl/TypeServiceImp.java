package com.gec.service.impl;

import com.gec.bean.PageBean;
import com.gec.bean.Type;
import com.gec.dao.TypeDao;
import com.gec.dao.impl.TypeDaoImp;
import com.gec.service.TypeService;

import java.util.List;

public class TypeServiceImp implements TypeService {
    TypeDao td=new TypeDaoImp();
    @Override
    public List<Type> findAll() {
        return td.findAll();
    }

    @Override
    public Type findById(int id) {
        return td.findById(id);
    }

    @Override
    public PageBean<Type> findPage(int pageNow, Type entity) {
        return null;
    }

    @Override
    public boolean save(Type entity) {
        return false;
    }

    @Override
    public boolean update(Type entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
