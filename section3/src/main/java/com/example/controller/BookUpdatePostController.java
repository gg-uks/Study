package com.example.controller;

import com.example.entity.BookDTO;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updatePost")
public class BookUpdatePostController extends HttpServlet { // POJO
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
                                                           throws ServletException, IOException {
        try {


            req.setCharacterEncoding("utf-8");
            //폼에서 넘어온 파라메터를 수집(DTO)
            //1.유효성검사
            String reqNum = req.getParameter("num");
            String title = req.getParameter("title");
            String reqPrice = req.getParameter("price");
            String author = req.getParameter("author");
            String reqPage = req.getParameter("page");

            if(title==null||title.trim().isEmpty()||author==null||author.trim().isEmpty()){
                System.out.println("제목과 저자는 필수 입력항목입니다");
                resp.sendRedirect("/s3/error?msg=1"); //error.jsp 화면에 오류메시지를 출력 해줄수 있게 만들수도 있다.
                return;
            }

            int price =0;
            int page =0;
            Long num = null;
            try {
                price = Integer.parseInt(reqPrice);
                page = Integer.parseInt(reqPage);
                num = Long.parseLong(reqNum);

            }catch (NumberFormatException e){
                System.out.println("가격과 페이지수는 정수여야 합니다");
                resp.sendRedirect("/s3/error?=msg2"); //error.jsp 화면에 오류메시지를 출력 해줄수 있게 만들수도 있다.
                return;
            }
            if(price<=0||page<=0){
                System.out.println("가격과 페이지수는 양의 정수여야 합니다");
                resp.sendRedirect("/s3/error?=msg3"); //error.jsp 화면에 오류메시지를 출력 해줄수 있게 만들수도 있다.
                return;
            }

            BookDTO dto = new BookDTO(num, title, price, author, page);
            BookDAOMyBatis dao = new BookDAOMyBatis();
            int cnt = dao.bookUpdate(dto);

            if(cnt>0){ //삭제여부는 비효율적인데 규칙상 한것
                //다시 리스트페이지로 이동 리다이렉트
//                resp.sendRedirect("/s3/list"); //리스트 페이지로
                resp.sendRedirect("/s3/view?num="+num); //상세보기 페이지로
                System.out.println("수정성공");
            }else {
                System.out.println("수정실패");
            }
        }catch (Exception e){
            e.printStackTrace();
            resp.sendRedirect("/s3/error?msg=0");
        }


    }
}
