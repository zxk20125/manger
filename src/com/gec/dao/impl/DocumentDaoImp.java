package com.gec.dao.impl;

import com.gec.bean.Document;
import com.gec.bean.Notice;
import com.gec.bean.PageBean;
import com.gec.dao.DocumentDao;
import com.gec.dao.UserDao;
import com.gec.util.DBUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DocumentDaoImp extends DBUtil<Document>implements DocumentDao {
    UserDao ud= new UserDaoImpl();

    @Override
    public List<Document> findAll() {
        return query("select * from document_inf");
    }

    @Override
    public Document findById(int id) {
        List<Document> documents = query("select * from document_inf where id=?", id);
        if(documents.size()>0){
            return documents.get(0);
        }
        return null;
    }

    @Override
    public PageBean<Document> findPage(int pageNow, Document entity) {
        PageBean<Document> pb = new PageBean<>();
        List<Object> obj = new ArrayList<>();
        pb.setPageNow(pageNow);
        String sql = "select count(id) from document_inf where 1=1";
        String str = "select * from document_inf where 1=1";
        if(entity.getTitle()!=null&&!entity.getTitle().equals("")){
            sql +=" and title like ?";
            str +=" and title like ?";
            obj.add("%"+entity.getTitle()+"%");
        }
        pb.setRowCount(getFunction(sql, obj.toArray()));
        str += " limit ?,?";
        obj.add((pageNow-1)*pb.getPageSize());
        obj.add(pb.getPageSize());
        pb.setList(query(str, obj.toArray()));
        return pb;
    }

    @Override
    public boolean save(Document entity) {
        return update("insert into document_inf(title,filename,filetype,filebytes,remark,create_date,user_id)value(?,?,?,?,?,?,?)",entity.getTitle(),entity.getFileName(),entity.getFiletype(),entity.getFileBytes(),entity.getRemark(),entity.getCreateDate(),entity.getUser().getId());

    }

    @Override
    public boolean update(Document entity) {
        return update("update document_inf set title=?,filename=?,filetype=?,filebytes=?,remark=?,create_date=?,user_id=? where id=?",entity.getTitle(),entity.getFileName(),entity.getFiletype(),entity.getFileBytes(),entity.getRemark(),entity.getCreateDate(),entity.getUser().getId(),entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return update("delete from document_inf where id=?",id);
    }

    @Override
    public Document getEntity(ResultSet rs) throws Exception {
        Document document= new Document();
        document.setId(rs.getInt(1));
        document.setTitle(rs.getString(2));
        document.setFileName(rs.getString(3));
        document.setFiletype(rs.getString(4));
        document.setFileBytes(rs.getBytes(5));
        document.setRemark(rs.getString(6));
        document.setCreateDate(rs.getDate(7));
        document.setUser(ud.findById(rs.getInt(8)));
        return document;
    }
}
