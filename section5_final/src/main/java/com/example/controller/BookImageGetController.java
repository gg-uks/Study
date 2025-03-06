package com.example.controller;

import com.example.entity.BookImageDTO;
import com.example.entity.ImageDTO;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//POJO 플랜 올드 자바 오브젝트
public class BookImageGetController implements Controller{
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int book_num=Integer.parseInt(req.getParameter("book_num"));
        BookDAOMyBatis dao = new BookDAOMyBatis();
        //보류
        //1.이미지 상세보기
        BookImageDTO book =dao.bookView(book_num);
        req.setAttribute("book",book);

        List<ImageDTO> mlist=dao.imageList(book_num);
        req.setAttribute("mlist",mlist);
        //이미지 등록 페이지 image.jsp
        return "image";
    }
}
