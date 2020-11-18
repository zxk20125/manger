package com.gec.servlet;

import com.gec.bean.Dept;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.DeptService;
import com.gec.service.impl.DeptServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/deptlist.action","/querydept.action","/addDept.action","/deptsaveOrUpdate.action","/editDept.action","/deptdel.action"})
public class DeptSevrlet extends HttpServlet {
    DeptService ds=new DeptServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(uri);
        int pageNow=1;
        if("deptlist.action".equals(uri)){
            String name=request.getParameter("name");
            String sta = request.getParameter("status");
            //创建一个用户对象
            Dept dept = new Dept();
            dept.setName(name);
            String index = request.getParameter("pageNow");
            pageNow = index!=null&&!index.equals("")?Integer.parseInt(index):1;
            PageBean<Dept> pb = ds.findPage(pageNow, dept);
            //保存查询信息
            request.setAttribute("pb", pb);
            //保存查询对象
            request.setAttribute("dept", dept);
            //跳转到页面
            request.getRequestDispatcher("WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
        }
        if("deptsaveOrUpdate.action".equals(uri)){
            String add = request.getParameter("add");
            String update = request.getParameter("update");
            System.out.println(add+update);
            if (update != null) {
                System.out.println("update");
                String id = request.getParameter("id");
                Dept dept = ds.findById(Integer.parseInt(id));
                dept.setName(request.getParameter("name"));
                dept.setRemark(request.getParameter("remark"));
                ds.update(dept);
            }
            else if (add != null) {
                System.out.println("add");
                Dept dept1=new Dept();
                dept1.setName(request.getParameter("name"));
                dept1.setRemark(request.getParameter("remark"));
                ds.save(dept1);
            }
            response.sendRedirect("deptlist.action");
        }
        if("editDept.action".equals(uri)){
            int id = Integer.parseInt(request.getParameter("id"));
            Dept dept = ds.findById(id);
            request.setAttribute("dept",dept);
            request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request,response);
        }
        if("addDept.action".equals(uri)){
            request.getRequestDispatcher("/WEB-INF/jsp/dept/deptadd.jsp").forward(request,response);
        }
        if("deptdel.action".equals(uri)){
            String id = request.getParameter("deptIds");
            ds.delete(Integer.parseInt(id));
            response.sendRedirect("deptlist.action");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
