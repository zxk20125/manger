package com.gec.dao.impl;

import com.gec.bean.Job;
import com.gec.bean.Notice;
import com.gec.bean.PageBean;
import com.gec.dao.NoticeDao;
import com.gec.dao.TypeDao;
import com.gec.util.DBUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NoticeDaoImp extends DBUtil<Notice> implements NoticeDao {
    TypeDao td= new TypeDaoImp();
    @Override
    public List<Notice> findAll() {
        return query("select * from notice_inf");
    }

    @Override
    public Notice findById(int id) {
        List<Notice> no = query("select * from notice_inf where id=?", id);
        if(no.size()>0){
            return no.get(0);
        }
        return null;
    }

    @Override
    public PageBean<Notice> findPage(int pageNow, Notice entity) {
        PageBean<Notice> pb = new PageBean<>();
        List<Object> obj = new ArrayList<>();
        pb.setPageNow(pageNow);
        String sql = "select count(id) from notice_inf where 1=1";
        String str = "select * from notice_inf where 1=1";
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
    public boolean save(Notice entity) {
        return update("insert into notice_inf(name,type_id,content,user_id,create_date)values(?,?,?,?,?)",entity.getName(),entity.getType().getId(),entity.getContent(),entity.getUser().getId(),entity.getCreateDate());

    }

    @Override
    public boolean update(Notice entity) {
        return update("update notice_inf set name=?,type_id=?,content=?,user_id=? where id=?",entity.getName(), entity.getType().getId(),entity.getContent(),entity.getUser().getId(),entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return update("delete from notice_inf where id=?",id);
    }

    @Override
    public Notice getEntity(ResultSet rs) throws Exception {
        Notice notice =new Notice();
        notice.setId(rs.getInt(1));
        notice.setName(rs.getString(2));
        notice.setCreateDate(rs.getDate(3));
        notice.setType(td.findById(rs.getInt(4)));
        notice.setContent(rs.getString(5));
        return notice;
    }
}
