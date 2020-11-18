package com.gec.dao.impl;

import com.gec.bean.PageBean;
import com.gec.bean.Type;
import com.gec.dao.TypeDao;
import com.gec.service.UserService;
import com.gec.service.impl.UserServiceImpl;
import com.gec.util.DBUtil;

import java.sql.ResultSet;
import java.util.List;

public class TypeDaoImp extends DBUtil<Type> implements TypeDao {
    UserService us= new UserServiceImpl();
    @Override
    public List<Type> findAll() {
        return query("select * from type_inf");
    }

    @Override
    public Type findById(int id) {
        List<Type> types = query("select * from type_inf where id=?", id);
        if (types.size()>0){
            return types.get(0);
        }
        return null;
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

    @Override
    public Type getEntity(ResultSet rs) throws Exception {
        Type type = new Type();
        type.setId(rs.getInt(1));
        type.setName(rs.getString(2));
        type.setCreateDate(rs.getDate(3));
        type.setUser(us.findById(rs.getInt(4)));
        return type;
    }
}
