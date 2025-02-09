package com.example.controller;

import com.example.entity.BookDTO;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update")
public class BookUpdateController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String cpath = req.getContextPath();
        //파라메터 5개를 가져오기 (num,title,price,author,page)
        //-> BookDTO : 1.파라미터 수집(DTO:바구니에 담기)
        req.setCharacterEncoding("utf-8");
        int num = Integer.parseInt(req.getParameter("num"));
        String title = req.getParameter("title");
        int price = Integer.parseInt(req.getParameter("price"));
        String author = req.getParameter("author");
        int page = Integer.parseInt(req.getParameter("page"));
        // 수집하기
        BookDTO dto = new BookDTO(num,title,price,author,page);
//        dto.setNum(num);
        dto.setTitle(title);
        dto.setPrice(price);
        dto.setAuthor(author);
        dto.setPage(page);
        //수집끝
//        BookDAO dao = new BookDAO();
        BookDAOMyBatis dao = new BookDAOMyBatis();
        dao.update(num,dto);//수정 성공
        resp.sendRedirect(cpath+"/list");
    }
}
