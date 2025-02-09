package com.example.controller;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")//원클릭 함수로 로케이션 되는 레지스터라는 요청이오면 처리함(매핑 개념 중요)
public class BookRegisterUIController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("실행됨");
        //register.jsp(등록폼)
        //forward(포워딩)
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/register.jsp"); //컨트롤러에대한 요청을 얻어와 경로로 보냄
        rd.forward(req,resp);
    }
}
