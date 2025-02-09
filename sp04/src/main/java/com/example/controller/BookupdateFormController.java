package com.example.controller;

import com.example.entity.BookDTO;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateForm")
public class BookupdateFormController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //요청파라미터를 얻어야한다  ?num=1
        int num = Integer.parseInt(req.getParameter("num"));//"3" -> 3
//        BookDAO dao = new BookDAO();
        BookDAOMyBatis dao = new BookDAOMyBatis();
        BookDTO dto =dao.getById(num);
        req.setAttribute("dto",dto); //dto(번지)--->|___________|
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/update.jsp");
        rd.forward(req,resp);
    }
}
