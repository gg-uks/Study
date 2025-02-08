package com.example.controller;

import com.example.entity.BookDTO;
import com.example.repository.BookDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/insert")
public class BookInsertController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //폼에서 넘어온 파라미터를 받아야한다(4개)
        req.setCharacterEncoding("utf-8");
        String title = req.getParameter("title");
        int price = Integer.parseInt(req.getParameter("price"));
        String author = req.getParameter("author");
        int page = Integer.parseInt(req.getParameter("page"));
        //DTO 담아서 -> DAO 에서 테이블에 저장 -> 다시 리스트 페이지로 전송
        BookDTO dto = new BookDTO();
        dto.setTitle(title);
        dto.setPrice(price);
        dto.setAuthor(author);
        dto.setPage(page);

        BookDAO dao = new BookDAO();
        int cnt = dao.bookInsert(dto);

        if(cnt>0){
            resp.sendRedirect("/MF01/list"); //다시 리다이렉트 리스트페이지로
        }else{
            throw new ServletException("error");
        }
    }
}
