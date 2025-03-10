package com.example.controller;

import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/reviewRemoveRest")
public class ReviewRemoveRestController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cpath = req.getContextPath();
        int review_id=Integer.parseInt(req.getParameter("review_id"));
        BookDAOMyBatis dao = new BookDAOMyBatis();
        int cnt = dao.reviewRemove(review_id);
        //fetch()요청--> 응답
        PrintWriter out = resp.getWriter();
        out.println(cnt); //응답->1
    }
}
