package com.gec.servlet;

import com.gec.bean.Notice;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.NoticeService;
import com.gec.service.TypeService;
import com.gec.service.impl.NoticeServiceImp;
import com.gec.service.impl.TypeServiceImp;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.acl.NotOwnerException;
import java.util.Date;

@WebServlet(urlPatterns = {"/noticelist.action", "/viewNotice.action", "/addNotice", "/noticesaveOrUpdate.action", "/noticedel.action"})
public class NoticeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(uri);
        NoticeService ns = new NoticeServiceImp();
        TypeService ts = new TypeServiceImp();
        int pageNow = 1;
        if ("noticelist.action".equals(uri)) {
            String name = request.getParameter("name");
            String sta = request.getParameter("status");
            //创建一个用户对象
            Notice notice = new Notice();
            notice.setName(name);
            String index = request.getParameter("pageNow");
            pageNow = index != null && !index.equals("") ? Integer.parseInt(index) : 1;
            PageBean<Notice> pb = ns.findPage(pageNow, notice);
            //保存查询信息
            request.setAttribute("pb", pb);
            //保存查询对象
            request.setAttribute("notice", notice);
            //跳转到页面
            request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
        }
        if ("viewNotice.action".equals(uri)) {
            String id = request.getParameter("id");
            System.out.println(ns.findById(Integer.parseInt(id)));
            request.setAttribute("notice", ns.findById(Integer.parseInt(id)));
            request.setAttribute("types", ts.findAll());
            request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
        }
        if ("addNotice".equals(uri)) {
            request.setAttribute("types", ts.findAll());
            String flag = request.getParameter("flag");
            request.setAttribute("flag",flag);
            request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp?flag=1").forward(request, response);
        }
        if ("noticesaveOrUpdate.action".equals(uri)) {
            String flag = request.getParameter("flag");
            String text = request.getParameter("text");
            String name = request.getParameter("name");
            String type_id = request.getParameter("type_id");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user_session");
            System.out.println(flag);
            if (flag.equals("1")) {
                Notice notice = new Notice();
                notice.setContent(text);
                notice.setName(name);
                notice.setType(ts.findById(Integer.parseInt(type_id)));
                notice.setUser(user);
                notice.setCreateDate(new Date());
                System.out.println(notice);
                ns.save(notice);
            } else {
                String id = request.getParameter("id");
                Notice nt = ns.findById(Integer.parseInt(id));
                nt.setId(Integer.valueOf(id));
                nt.setContent(text);
                nt.setName(name);
                nt.setType(ts.findById(Integer.parseInt(type_id)));
                nt.setUser(user);
                nt.setModifyDate(new Date());
                System.out.println(nt);
                ns.update(nt);
            }
            response.sendRedirect("noticelist.action");
        }
        if ("noticedel.action".equals(uri)) {
            String id = request.getParameter("noticeIds");
            ns.delete(Integer.parseInt(id));
            response.sendRedirect("noticelist.action");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
