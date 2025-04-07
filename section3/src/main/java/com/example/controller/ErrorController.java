package com.example.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

// 중요한부분
@WebServlet("/error")
public class ErrorController extends HelloServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String msgCode=req.getParameter("msg");
        String messageKey="error."+msgCode;//error.1

        ResourceBundle messages = ResourceBundle.getBundle("message",req.getLocale());
        String errorMessage=messages.getString(messageKey);
        req.setAttribute("errorMessage",errorMessage);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/error.jsp");
        rd.forward(req,resp);
    }
}
