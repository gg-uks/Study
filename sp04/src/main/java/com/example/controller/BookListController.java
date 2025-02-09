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
import java.util.List;

//http://localhost:8081/MF01/list
@WebServlet("/list")
public class BookListController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        BookDAO dao = new BookDAO();
        BookDAOMyBatis dao = new BookDAOMyBatis();//마이바티스로 바꿧을 가정하에
        List<BookDTO>list=dao.bookList();
        //객체바인딩 기술
        req.setAttribute("list",list);
        //forward,dispatcher
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/list.jsp");
        rd.forward(req,resp); //forward기법으로 뿌려준다 리퀘스트 리스폰스로

    }
}
