package com.gec.servlet;

import com.gec.bean.Dept;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.JobService;
import com.gec.service.impl.JobServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/joblist.action", "/queryjob.action", "/jobdel.action", "/addJob.action", "/editJob.action", "/jobsaveOrUpdate.action"})
public class JobSevrlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(uri);
        JobService js = new JobServiceImpl();
        int pageNow = 1;
        if ("joblist.action".equals(uri)) {
            String name = request.getParameter("name");
            String remark = request.getParameter("remark");
            //创建一个用户对象
            Job job = new Job(name,remark);
            String index = request.getParameter("pageNow");
            pageNow = index!=null&&!index.equals("")?Integer.parseInt(index):1;
            PageBean<Job> pb = js.findPage(pageNow, job);
            //保存查询信息
            request.setAttribute("pb", pb);
            //保存查询对象
            request.setAttribute("job", job);
            System.out.println(pb);
            request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
        }
        if ("jobdel.action".equals(uri)) {
            String[] jobIds = request.getParameterValues("jobIds");
            for (String id : jobIds) {
                js.delete(Integer.parseInt(id));
            }
            response.sendRedirect("joblist.action");
        }
        if ("jobsaveOrUpdate.action".equals(uri)) {
            String add = request.getParameter("add");
            String update = request.getParameter("update");
            System.out.println(update+add);
            if (update != null) {
                String id = request.getParameter("id");
                Job job = js.findById(Integer.parseInt(id));
                job.setName(request.getParameter("name"));
                job.setRemark(request.getParameter("remark"));
                js.update(job);
            } else if (add != null) {
                Job job1 = new Job();
                job1.setName(request.getParameter("name"));
                job1.setRemark(request.getParameter("remark"));
                js.save(job1);
            }
            response.sendRedirect("joblist.action");
        }
        if ("editJob.action".equals(uri)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Job job = js.findById(id);
            request.setAttribute("job", job);
            request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp?id="+job.getId()).forward(request, response);
        }
        if ("addJob.action".equals(uri)) {
            request.getRequestDispatcher("/WEB-INF/jsp/job/jobadd.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
