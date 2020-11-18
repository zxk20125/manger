package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.UserDao;
import com.gec.util.DBUtil;

public class UserDaoImpl extends DBUtil<User> implements UserDao {

    @Override
    public List<User> findAll() {
        List<User> users = query("select * from user_inf");
        return users;
    }

    @Override
    public User findById(int id) {
        List<User> list = query("select * from user_inf where id=?", id);
        if (list.size() > 0) {
            System.out.println(1);
            return list.get(0);
        }
        return null;
    }

    @Override
    public PageBean<User> findPage(int pageNow, User entity) {
        // TODO Auto-generated method stub
        PageBean<User> pb = new PageBean<>();
        List<Object> obj = new ArrayList<>();
        pb.setPageNow(pageNow);
        String sql = "select count(id) from user_inf where 1=1";
        String str = "select * from user_inf where 1=1";
        if(entity.getLoginname()!=null&&!entity.getLoginname().equals("")){
            sql +=" and loginname like ?";
            str +=" and loginname like ?";
            obj.add("%"+entity.getLoginname()+"%");
        }
        if(entity.getUsername()!=null && !entity.getUsername().equals("")){
            sql += " and username like ?";
            str += " and username like ?";
            obj.add("%"+entity.getUsername()+"%");
        }
        if(entity.getStatus()>0){
            sql += " and status=?";
            str += " and status=?";
            obj.add(entity.getStatus());
        }
        pb.setRowCount(getFunction(sql, obj.toArray()));
        str += " limit ?,?";
        obj.add((pageNow-1)*pb.getPageSize());
        obj.add(pb.getPageSize());
        pb.setList(query(str, obj.toArray()));
        return pb;
    }

    @Override
    public boolean save(User entity) {
        return update("insert into user_inf(username,loginname,password,status)values(?,?,?,?)",entity.getUsername(),entity.getLoginname(),entity.getPassword(),entity.getStatus());
    }

    @Override
    public boolean update(User entity) {
        return update("update user_inf set loginname=?,password=?,username=?,status=? where id=?", entity.getLoginname(), entity.getPassword(), entity.getUsername(), entity.getStatus(), entity.getId());
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        return update("delete from user_inf where id=?",id);
    }

    @Override
    public User login(String loginname, String password) {
        List<User> list = query("select * from user_inf where loginname=? and password=?", loginname, password);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public List<User> findUser(String username, String status) {
        List<User> list = null;
        if (null != username)
            if (null != status)
                return query("select * from user_inf where username like ? and status =?", "%"+username+"%", status);
            else
                list = query("select * from user_inf where username like ?", "%"+username+"%");
        if (null != status)
            list = query("select * from user_inf where status=?", status);
        return list;
    }

    @Override
    public User getEntity(ResultSet rs) throws Exception {
        User user = new User();
        user.setId(rs.getInt(1));
        user.setLoginname(rs.getString(2));
        user.setPassword(rs.getString(3));
        user.setStatus(rs.getInt(4));
        user.setCreatedate(rs.getDate(5));
        user.setUsername(rs.getString(6));
        return user;
    }

}
