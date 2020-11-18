package com.gec.dao;

import com.gec.bean.Dept;
import com.gec.bean.Job;

import java.util.List;

public interface JobDao extends BaseDao<Job> {
    List<Job> findJob(String name);

}
