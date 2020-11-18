package com.gec.dao.impl;

import com.gec.bean.Dept;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.DeptDao;
import com.gec.util.DBUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeptDaoImp  extends DBUtil<Dept> implements DeptDao  {
    @Override
    public List<Dept> findAll() {
        return query("select * from dept_inf");
    }

    @Override
    public Dept findById(int id) {
        List<Dept> dep = query("select * from dept_inf where id=?", id);
        if(dep.size()>0){
            return dep.get(0);
        }
        return null;
    }

    @Override
    public PageBean<Dept> findPage(int pageNow, Dept entity) {
        PageBean<Dept> pb = new PageBean<>();
        List<Object> obj = new ArrayList<>();
        pb.setPageNow(pageNow);
        String sql = "select count(id) from dept_inf where 1=1";
        String str = "select * from dept_inf where 1=1";
        if(entity.getName()!=null&&!entity.getName().equals("")){
            sql +=" and NAME like ?";
            str +=" and NAME like ?";
            obj.add("%"+entity.getName()+"%");
        }
        pb.setRowCount(getFunction(sql, obj.toArray()));
        str += " limit ?,?";
        obj.add((pageNow-1)*pb.getPageSize());
        obj.add(pb.getPageSize());
        pb.setList(query(str, obj.toArray()));
        return pb;
    }

    @Override
    public boolean save(Dept entity) {
        return update("insert into dept_inf(name,remark)values(?,?)",entity.getName(),entity.getRemark());
    }

    @Override
    public boolean update(Dept entity) {
        return update("update dept_inf set name=?,remark=? where id=?",entity.getName(),entity.getRemark(),entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return update("delete from dept_inf where id=?",id);
    }

    @Override
    public Dept getEntity(ResultSet rs) throws Exception {
        Dept dept = new Dept();
        dept.setId(rs.getInt(1));
        dept.setName(rs.getString(2));
        dept.setRemark(rs.getString(3));
        return dept;
    }

    @Override
    public Dept findDept(String name) {
        List<Dept> dep = query("select * from dept_inf where name like ?", "%"+name+"%");
        if(dep.size()>0){
            return dep.get(0);
        }
        return null;
    }
}
