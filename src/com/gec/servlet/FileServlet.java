package com.gec.servlet;

import com.gec.bean.Document;
import com.gec.bean.Notice;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.DocumentService;
import com.gec.service.impl.DocumentServiceImp;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sun.util.resources.cldr.az.CurrencyNames_az_Cyrl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/documentlist.action", "/documentadd.action","/documentaddsave.action","/updateDocument","/downloadDocument","/UpdatesaveDocument.action","/removeDocument"})
public class FileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(uri);
        DocumentService ds= new DocumentServiceImp();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user_session");
        int pageNow=1;
        if ("documentadd.action".equals(uri)) {
            System.out.println(user);
            request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
        }
        if ("documentaddsave.action".equals(uri)) {
            Document document = new Document();
            boolean flag = false;  //用于判断文件是否上传成功
            /*
             * 是通过upload上传组件进行获取内容
             */
            //1.判断是否为二进制流提交内容
            if (ServletFileUpload.isMultipartContent(request)) {
                //2.创建一个存储工厂来存储数据内容
                //DiskFileItemFactory 是一个内存数据保存工厂对象
                FileItemFactory factory = new DiskFileItemFactory();
                //3.获取到组件中servletFileUpload,将所解析的内容放入工厂,通过工厂转换为每一项文件
                ServletFileUpload upload = new ServletFileUpload(factory);
                //4.通过servletFileUpload类中的parseRequest将request中的数据转换为FileItem
                try {
                    @SuppressWarnings("unchecked")
                    List<FileItem> list = upload.parseRequest(request);
                    if (list != null) {
                        //5.循环所有项,
                        for (FileItem item : list) {
                            //判断file是否为普通表单文件,isFormField判断 是普通表单数据则返回true 否则返回false
                            if (item.isFormField()) {

                                String title = request.getParameter("title");
                                if ("title".equals(item.getFieldName())) {
                                    //如果找到对应属性名,直接获取对应的值
                                    System.out.println("title:"+item.getString());
                                    document.setTitle(item.getString());
                                    // .setUserName(item.getString("utf-8"));
                                }
                                if ("remark".equals(item.getFieldName())) {
                                    System.out.println("remark:"+item.getString());
                                    document.setRemark(item.getString());
                                }
                            } else {
                                //6.获取到要存储的文件夹
                                String path = request.getServletContext().getRealPath("/upload");
                                System.out.println(path);
                                File file = new File(path);
                                //判断该路径是否存在,如果不存在就新建
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                //获取文件名
                                String fileName = item.getName();
//                                fileName = fileName.substring(0, fileName.lastIndexOf(".")) + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
                                File newFile = new File(file, fileName);
                                //将item写出到指定文件中newFile
                                item.write(newFile);
                                flag = true;
                                //将文件名保存在对象中
                                FileInputStream fin=new FileInputStream(path+"\\"+fileName);
                                int len = 0;
                                byte[] bs = new byte[1024];
                                fin.read(bs);
                                document.setFileBytes(bs);
                                document.setFiletype(fileName.split("\\.")[1]);
                                document.setFileName(fileName);
                                document.setCreateDate(new Date());
                                document.setUser(user);
                                ds.save(document);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //普通提交方式获取
                flag = true;
            }
//            if (flag) {
//                request.setAttribute("user", user);
//                request.getRequestDispatcher("index.jsp").forward(request, response);
//            } else {
//                response.sendRedirect("register.jsp");
//            }
            response.sendRedirect("documentlist.action");
        }
        if("documentlist.action".equals(uri)){
            String name = request.getParameter("title");
            String sta = request.getParameter("status");
            //创建一个用户对象
            Document doc = new Document();
            doc.setTitle(name);
            String index = request.getParameter("pageNow");
            pageNow = index != null && !index.equals("") ? Integer.parseInt(index) : 1;
            PageBean<Document> pb = ds.findPage(pageNow, doc);
            //保存查询信息
            request.setAttribute("pb", pb);
            //保存查询对象
            request.setAttribute("document", doc);
            //跳转到页面
            request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request,response);
        }
        if("updateDocument".equals(uri)){
            String id = request.getParameter("id");
            Document doc = ds.findById(Integer.parseInt(id));
            request.setAttribute("document",doc);
            request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request,response);
        }
        if("downloadDocument".equals(uri)){
            String path = request.getServletContext().getRealPath("/upload");
            //获取要下载的文件名称
            String id = request.getParameter("id");
            Document doc = ds.findById(Integer.parseInt(id));
            //通过输入流,将文件读取
            //File.separator 这是File类提供的一个自适应斜线静态常量
            InputStream in = new FileInputStream(path+File.separator+doc.getFileName());
            //设置响应头的内容,设置响应类型为下载类型,并设置下载文件的名称
            response.setHeader("Content-Disposition", "attachment;filename="+doc.getFileName());
            //创建输出流对象,将流输出到客户端
            ServletOutputStream out = response.getOutputStream();
            //将流输出
            int len = 0;
            byte[] bs = new byte[1024];
            while((len=in.read(bs))>0){
                out.write(bs, 0, len);
            }
            //关闭流
            out.flush();
            out.close();
        }
        if("UpdatesaveDocument.action".equals(uri)){
            String id = request.getParameter("id");
            Document doc = ds.findById(Integer.parseInt(id));
            String title = request.getParameter("title");
            String remark = request.getParameter("remark");
            String file = request.getParameter("file");
            doc.setTitle(title);
            doc.setRemark(remark);
            doc.setFileBytes(file.getBytes());
            ds.update(doc);
            response.sendRedirect("documentlist.action");
        }
        if("removeDocument".equals(uri)){
            String id = request.getParameter("ids");
            ds.delete(Integer.parseInt(id));
            response.sendRedirect("documentlist.action");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
