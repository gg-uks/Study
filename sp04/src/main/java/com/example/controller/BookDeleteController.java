package com.example.controller;

import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class BookDeleteController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String cpath = req.getContextPath();
        //?num=1
        //요청파라미터를 얻어야한다  ?num=1
        int num = Integer.parseInt(req.getParameter("num"));//"3" -> 3
//        BookDAO dao = new BookDAO();
        BookDAOMyBatis dao = new BookDAOMyBatis();
        dao.deleteById(num);//삭제 성공
        resp.sendRedirect(cpath+"/list");
    }
}
