package com.gec.dao.impl;

import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.PageBean;
import com.gec.dao.EmployeeDao;
import com.gec.service.DeptService;
import com.gec.service.JobService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.service.impl.JobServiceImpl;
import com.gec.util.DBUtil;
import sun.font.TrueTypeGlyphMapper;

import java.sql.ResultSet;
import java.util.*;

public class EmployeeDaoImp extends DBUtil<Employee> implements EmployeeDao {
    DeptService ds = new DeptServiceImpl();
    JobService js = new JobServiceImpl();

    @Override
    public List<Employee> findAll() {
        return query("select * from employee_inf");
    }

    @Override
    public Employee findById(int id) {
        List<Employee> emp = query("select * from employee_inf where id=?", id);
        if (emp.size() > 0) {
            return emp.get(0);
        }
        return null;
    }

    @Override
    public PageBean<Employee> findPage(int pageNow, Employee entity) {
        PageBean<Employee> pb = new PageBean<>();
        List<Object> obj = new ArrayList<>();
        pb.setPageNow(pageNow);
        String sql = "select count(id) from employee_inf where 1=1";
        String str = "select * from employee_inf where 1=1";
        if(entity.getName()!=null&&!entity.getName().equals("")){
            sql +=" and NAME like ?";
            str +=" and NAME like ?";
            obj.add("%"+entity.getName()+"%");
        }
        if(entity.getSex() != null){
            sql +=" and sex like ?";
            str +=" and sex like ?";
            obj.add("%"+entity.getSex()+"%");
        }
        if(null!=entity.getDept()){
            sql +=" and dept_id like ?";
            str +=" and dept_id like ?";
            obj.add("%"+entity.getDept().getId()+"%");
        }
        if(null!=entity.getJob()){
            sql +=" and job_id like ?";
            str +=" and job_id like ?";
            obj.add("%"+entity.getJob().getId()+"%");
        }
        if(entity.getPhone()!=null&&!entity.getPhone().equals("")){
            sql +=" and phone like ?";
            str +=" and phone like ?";
            obj.add("%"+entity.getPhone()+"%");
        }
        if(entity.getCardId()!=null&&!entity.getCardId().equals("")){
            sql +=" and card_id like ?";
            str +=" and card_id like ?";
            obj.add("%"+entity.getCardId()+"%");
        }
        pb.setRowCount(getFunction(sql, obj.toArray()));
        str += " limit ?,?";
        obj.add((pageNow-1)*pb.getPageSize());
        obj.add(pb.getPageSize());
        pb.setList(query(str, obj.toArray()));
        return pb;
    }

    @Override
    public boolean save(Employee entity) {
        return update("insert into employee_inf(name,card_Id,address,post_Code,tel,phone,qq_Num,email,sex,party,birthday,race,education,speciality,hobby,remark,dept_id,job_id,create_Date)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", entity.getName(), entity.getCardId(), entity.getAddress(),
                entity.getPostCode(), entity.getTel(), entity.getPhone(), entity.getQqNum(), entity.getEmail(), entity.getSex(), entity.getParty(), entity.getBirthday(), entity.getRace(), entity.getEducation(), entity.getSpeciality(), entity.getHobby(), entity.getRemark(), entity.getDept().getId(), entity.getJob().getId(), entity.getCreateDate());

    }

    @Override
    public boolean update(Employee entity) {
        return update("update employee_inf set name=?,card_Id=?,address=?,post_Code=?,tel=?,phone=?,qq_Num=?,email=?,sex=?,party=?,birthday=?,race=?,education=?,speciality=?,hobby=?,remark=?,dept_id=?,job_id=?,create_Date=? where id =?", entity.getName(), entity.getCardId(), entity.getAddress(),
                entity.getPostCode(), entity.getTel(), entity.getPhone(), entity.getQqNum(), entity.getEmail(), entity.getSex(), entity.getParty(), entity.getBirthday(), entity.getRace(), entity.getEducation(), entity.getSpeciality(), entity.getHobby(), entity.getRemark(), entity.getDept().getId(), entity.getJob().getId(), entity.getCreateDate(), entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return update("delete from employee_inf where id=?", id);
    }

    @Override
    public Employee getEntity(ResultSet rs) throws Exception {
        Employee ep = new Employee();
        ep.setId(rs.getInt(1));
        ep.setName(rs.getString(2));
        ep.setCardId(rs.getString(3));
        ep.setAddress(rs.getString(4));
        ep.setPostCode(rs.getString(5));
        ep.setTel(rs.getString(6));
        ep.setPhone(rs.getString(7));
        ep.setQqNum(rs.getString(8));
        ep.setEmail(rs.getString(9));
        ep.setSex(rs.getInt(10));
        ep.setParty(rs.getString(11));
        ep.setBirthday(rs.getDate(12));
        ep.setRace(rs.getString(13));
        ep.setEducation(rs.getString(14));
        ep.setSpeciality(rs.getString(15));
        ep.setHobby(rs.getString(16));
        ep.setRemark(rs.getString(17));
        ep.setCreateDate(rs.getDate(18));
        ep.setJob(js.findById(rs.getInt(20)));
        ep.setDept(ds.findById(rs.getInt(21)));
        return ep;
    }

    @Override
    public List<Employee> findEmployee(HashMap<String, Object> hm) {
        StringBuilder sql = new StringBuilder("select * from employee_inf where ");
        for (String key : hm.keySet()) {
            Object o = hm.get(key);
            if (!o.equals("")) {
                sql.append(key).append(" like ").append("'%").append(o).append("%' ").append("and ");
            }
        }
        if (hm.keySet().size() >= 1) {
            String s = sql.substring(0, sql.lastIndexOf("and"));
            System.out.println(s);
            return query(s);
        }
        return null;
    }

    @Override
    public boolean findIds(String id) {
        List<Employee> query = query("select * from employee_inf where card_id=?", id);
        if(query.size()>0)
            return true;
        return false;
    }
}
