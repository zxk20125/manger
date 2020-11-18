package com.gec.service.impl;

import com.gec.bean.Notice;
import com.gec.bean.PageBean;
import com.gec.dao.NoticeDao;
import com.gec.dao.impl.NoticeDaoImp;
import com.gec.service.NoticeService;

import java.util.List;

public class NoticeServiceImp implements NoticeService {
    NoticeDao nd=new NoticeDaoImp();
    @Override
    public List<Notice> findAll() {
        return nd.findAll();
    }

    @Override
    public Notice findById(int id) {
        return nd.findById(id);
    }

    @Override
    public PageBean<Notice> findPage(int pageNow, Notice entity) {
        return nd.findPage(pageNow,entity);
    }

    @Override
    public boolean save(Notice entity) {
        return nd.save(entity);
    }

    @Override
    public boolean update(Notice entity) {
        return nd.update(entity);
    }

    @Override
    public boolean delete(int id) {
        return nd.delete(id);
    }
}
