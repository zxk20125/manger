package com.gec.dao.impl;

import com.gec.bean.Dept;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.dao.DeptDao;
import com.gec.dao.JobDao;
import com.gec.util.DBUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImp extends DBUtil<Job> implements JobDao {
    @Override
    public List<Job> findAll() {
        return query("select * from job_inf");
    }

    @Override
    public Job findById(int id) {
        List<Job> j = query("select * from job_inf where id=?", id);
        if(j.size()>0){
            return j.get(0);
        }
        return null;
    }

    @Override
    public PageBean<Job> findPage(int pageNow, Job entity) {
        PageBean<Job> pb = new PageBean<>();
        List<Object> obj = new ArrayList<>();
        pb.setPageNow(pageNow);
        String sql = "select count(id) from job_inf where 1=1";
        String str = "select * from job_inf where 1=1";
        if(entity.getName()!=null&&!entity.getName().equals("")){
            sql +=" and NAME like ?";
            str +=" and NAME like ?";
            obj.add("%"+entity.getName()+"%");
        }
        if(entity.getRemark()!=null && !entity.getRemark().equals("")){
            sql += " and REMARK like ?";
            str += " and REMARK like ?";
            obj.add("%"+entity.getRemark()+"%");
        }
        pb.setRowCount(getFunction(sql, obj.toArray()));
        str += " limit ?,?";
        obj.add((pageNow-1)*pb.getPageSize());
        obj.add(pb.getPageSize());
        pb.setList(query(str, obj.toArray()));
        return pb;
    }

    @Override
    public boolean save(Job entity) {
        return update("insert into job_inf(name,remark)values(?,?)",entity.getName(),entity.getRemark());
    }

    @Override
    public boolean update(Job entity) {
        return update("update job_inf set name=?,remark=? where id=?",entity.getName(),entity.getRemark(),entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return update("delete from job_inf where id=?",id);
    }

    @Override
    public Job getEntity(ResultSet rs) throws Exception {
        Job job=new Job();
        job.setId(rs.getInt(1));
        job.setName(rs.getString(2));
        job.setRemark(rs.getString(3));
        return job;
    }

    @Override
    public List<Job> findJob(String name) {
        return query("select * from job_inf where name like ?", "%"+name+"%");
    }
}
