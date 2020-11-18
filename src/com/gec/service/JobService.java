package com.gec.service;

import com.gec.bean.Dept;
import com.gec.bean.Job;
import com.gec.dao.DeptDao;
import com.gec.dao.JobDao;

import java.util.List;


public interface JobService extends JobDao {
    List<Job> findJob(String name);
}
