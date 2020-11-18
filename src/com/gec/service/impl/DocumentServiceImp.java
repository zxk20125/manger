package com.gec.service.impl;

import com.gec.bean.Document;
import com.gec.bean.PageBean;
import com.gec.dao.DocumentDao;
import com.gec.dao.impl.DocumentDaoImp;
import com.gec.service.DocumentService;

import java.util.List;

public class DocumentServiceImp implements DocumentService {
    DocumentDao dd=new DocumentDaoImp();
    @Override
    public List<Document> findAll() {
        return dd.findAll();
    }

    @Override
    public Document findById(int id) {
        return dd.findById(id);
    }

    @Override
    public PageBean<Document> findPage(int pageNow, Document entity) {
        return dd.findPage(pageNow,entity);
    }

    @Override
    public boolean save(Document entity) {
        return dd.save(entity);
    }

    @Override
    public boolean update(Document entity) {
        return dd.update(entity);
    }

    @Override
    public boolean delete(int id) {
        return dd.delete(id);
    }
}
