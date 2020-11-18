package com.gec.servlet;

import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.dao.EmployeeDao;
import com.gec.service.DeptService;
import com.gec.service.EmployeeService;
import com.gec.service.JobService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.service.impl.EmployeeServiceImp;
import com.gec.service.impl.JobServiceImpl;
import com.gec.util.DBUtil;

import javax.persistence.Id;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = {"/employeelist.action", "/addEmployee.action", "/updateEmployee.action", "/updateSaveEmployee.action", "/addSaveEmployee.action", "/employeedel.action", "/employeequery.action", "/getcardid.action"})
public class EmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeService es = new EmployeeServiceImp();
        DBUtil dbUtil = new DBUtil() {
            @Override
            public Object getEntity(ResultSet rs) throws Exception {
                return null;
            }
        };
        DeptService ds = new DeptServiceImpl();
        JobService js = new JobServiceImpl();
        String uri = request.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(uri);
        List<Dept> depts = ds.findAll();
        List<Job> jobs = js.findAll();
        int pageNow = 1;
        if ("employeelist.action".equals(uri)) {
            String name = request.getParameter("name");
            //创建一个用户对象
            Employee ep = new Employee();
            String index = request.getParameter("pageNow");
            pageNow = index != null && !index.equals("") ? Integer.parseInt(index) : 1;
            String sex = request.getParameter("sex");
            String job_id = request.getParameter("job_id");
            String dept_id = request.getParameter("dept_id");
            if (null != sex && !sex.equals(""))
                ep.setSex(Integer.parseInt(sex));
            if (null != job_id && !job_id.equals(""))
                ep.setJob(js.findById(Integer.parseInt(job_id)));
            if (null != dept_id && !dept_id.equals(""))
                ep.setDept(ds.findById(Integer.parseInt(dept_id)));
            ep.setName(request.getParameter("name"));
            ep.setCardId(request.getParameter("cardId"));
            ep.setPhone(request.getParameter("phone"));
            PageBean<Employee> pb = es.findPage(pageNow, ep);
            System.out.println(pb.getList());
            System.out.println(job_id);
            request.setAttribute("pb", pb);
            //保存查询对象
            request.setAttribute("deptList", ds.findAll());
            request.setAttribute("jobList", js.findAll());
            request.setAttribute("employee", ep);
            request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
        }
        if ("addEmployee.action".equals(uri)) {
            request.setAttribute("deptList", depts);
            request.setAttribute("jobList", jobs);
            request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeadd.jsp").forward(request, response);
        }
        if ("updateEmployee.action".equals(uri)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Employee emp = es.findById(id);
            request.setAttribute("deptList", depts);
            request.setAttribute("jobList", jobs);
            request.setAttribute("employee", emp);
            request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
        }
        if ("updateSaveEmployee.action".equals(uri)) {
            SimpleDateFormat sip = new SimpleDateFormat("yyyy-MM-dd");
            int id = Integer.parseInt(request.getParameter("id"));
            Employee ep = es.findById(id);
            ep.setName(request.getParameter("name"));
            ep.setCardId(request.getParameter("cardId"));
            ep.setSex(Integer.parseInt(request.getParameter("sex")));
            ep.setJob(js.findById(Integer.parseInt(request.getParameter("job_id"))));
            ep.setEducation(request.getParameter("education"));
            ep.setEmail(request.getParameter("email"));
            ep.setPhone(request.getParameter("phone"));
            ep.setTel(request.getParameter("tel"));
            ep.setParty(request.getParameter("party"));
            ep.setQqNum(request.getParameter("qqNum"));
            ep.setAddress(request.getParameter("address"));
            ep.setPostCode(request.getParameter("postCode"));
            ep.setRace(request.getParameter("race"));
            ;
            ep.setSpeciality(request.getParameter("speciality"));
            ep.setHobby(request.getParameter("hobby"));
            ep.setDept(ds.findById(Integer.parseInt(request.getParameter("dept_id"))));
            Date birthday = null;
            try {
                birthday = sip.parse(request.getParameter("birthday"));
                ep.setBirthday(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            es.update(ep);
            response.sendRedirect("employeelist.action");
        }
        if ("addSaveEmployee.action".equals(uri)) {
            SimpleDateFormat sip = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String name = request.getParameter("name");
            String cardId = request.getParameter("cardId");
            int sex = Integer.parseInt(request.getParameter("sex"));
            int job_id = Integer.parseInt(request.getParameter("job_id"));
            String education = request.getParameter("education");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String tel = request.getParameter("tel");
            String party = request.getParameter("party");
            String qqNum = request.getParameter("qqNum");
            String address = request.getParameter("address");
            String postCode = request.getParameter("postCode");
            String race = request.getParameter("race");
            String speciality = request.getParameter("speciality");
            String hobby = request.getParameter("hobby");
            int dept_id = Integer.parseInt(request.getParameter("dept_id"));
            Date birthday = null;
            try {
                birthday = sip.parse(request.getParameter("birthday"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Employee ep = new Employee(name, cardId, education, email, phone, tel, party, qqNum, address, postCode, birthday, race, speciality, hobby, sex);
            ep.setDept(ds.findById(dept_id));
            ep.setJob(js.findById(job_id));
            ep.setCreateDate(new Date());
            es.save(ep);
            response.sendRedirect("employeelist.action");
        }
        if ("employeedel.action".equals(uri)) {
            es.delete(Integer.parseInt(request.getParameter("epID")));
            response.sendRedirect("employeelist.action");
        }
        if ("employeequery.action".equals(uri)) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("job_id", request.getParameter("job_id"));
            hm.put("name", request.getParameter("name"));
            hm.put("sex", request.getParameter("sex"));
            hm.put("dept_id", request.getParameter("dept_id"));
            hm.put("phone", request.getParameter("phone"));
            hm.put("card_Id", request.getParameter("cardId"));
            List<Employee> eps = es.findEmployee(hm);
            List<Employee> epss = es.findAll();
            request.setAttribute("deptList", ds.findAll());
            request.setAttribute("jobList", js.findAll());
            request.setAttribute("employeelist", eps);
            request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
        }
        if ("getcardid.action".equals(uri)) {
            String cardID = request.getParameter("cardId");
            boolean ids = es.findIds(cardID);
            PrintWriter writer = response.getWriter();
            if (ids) {
                writer.println("身份证已存在！！");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
