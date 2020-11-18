package com.gec.servlet;

import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.UserService;
import com.gec.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(urlPatterns = {"/userlist.action", "/useradd.action", "/queryUser.action", "/userUpdate.action", "/userSave.action","/userdel.action"})
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserService us = new UserServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String uri = request.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(uri);
        List<User> users = null;
        int pageNow=1;
        if ("userlist.action".equals(uri)) {
            String loginname = request.getParameter("loginname");
            String username = request.getParameter("username");
            String sta = request.getParameter("status");
            int status = sta!=null&&!sta.equals("") ? Integer.parseInt(sta):0;
            //创建一个用户对象
            User user = new User(username,status);
            user.setLoginname(loginname);
            String index = request.getParameter("pageNow");
            pageNow = index!=null&&!index.equals("")?Integer.parseInt(index):1;
            PageBean<User> pb = us.findPage(pageNow, user);

            //保存查询信息
            request.setAttribute("pb", pb);
            //保存查询对象
            request.setAttribute("user", user);
            //跳转到页面
            request.getRequestDispatcher("WEB-INF/jsp/user/userlist.jsp").forward(request, response);
        }
        if ("userUpdate.action".equals(uri)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = us.findById(id);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp?id="+id).forward(request, response);
        }
        if ("userSave.action".equals(uri)) {

            String upd = request.getParameter("upd");
            String add = request.getParameter("add");
            if (upd != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                User user = us.findById(id);
                user.setLoginname(request.getParameter("loginname"));
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                user.setStatus(Integer.parseInt(request.getParameter("status")));
                us.update(user);
            }
            else if (add != null) {
                User user1 = new User();
                user1.setLoginname(request.getParameter("loginname"));
                user1.setUsername(request.getParameter("username"));
                user1.setPassword(request.getParameter("password"));
                user1.setStatus(Integer.parseInt(request.getParameter("status")));
                us.save(user1);
            }
            response.sendRedirect("userlist.action");
        }
        if("useradd.action".equals(uri)){
            request.setAttribute("method","add");
            request.getRequestDispatcher("/WEB-INF/jsp/user/useradd.jsp").forward(request,response);
        }
        if("userdel.action".equals(uri)){
            String id = request.getParameter("userIds");
            us.delete(Integer.parseInt(id));
            response.sendRedirect("userlist.action");
        }
    }
}
