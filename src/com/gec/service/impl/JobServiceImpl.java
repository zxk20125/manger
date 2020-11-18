package com.gec.service.impl;

import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.dao.JobDao;
import com.gec.dao.impl.JobDaoImp;
import com.gec.service.JobService;

import java.util.List;

public class JobServiceImpl implements JobService {
	JobDao jd= new JobDaoImp();
	@Override
	public List<Job> findAll() {
		return jd.findAll();
	}

	@Override
	public Job findById(int id) {
		return jd.findById(id);
	}

	@Override
	public PageBean<Job> findPage(int pageNow, Job entity) {
		return jd.findPage(pageNow,entity);
	}

	@Override
	public boolean save(Job entity) {
		return jd.save(entity);
	}

	@Override
	public boolean update(Job entity) {
		return jd.update(entity);
	}

	@Override
	public boolean delete(int id) {
		return jd.delete(id);
	}

	@Override
	public List<Job> findJob(String name) {
		return jd.findJob(name);
	}
}
