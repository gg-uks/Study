package com.example.controller;

import com.example.entity.CartDTO;
import com.example.repository.BookDAOMyBatis;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCartController implements Controller{
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        //req.getInputStream() => HTTP 요청에서 전송된 JSON 데이터를 스트림 형태로 읽음
        //objectMapper.readValue() → Jackson이 스트림에서 읽은 JSON 데이터를 CartDTO.class 타입의 객체로 변환 (역직렬화)
        //변환된 객체를 cartDTO에 저장
        //json데이터가 키값이랑 DTO가 일치해야 DAO에서 작업이 가능하다 조건절로
        CartDTO cartDTO = objectMapper.readValue(req.getInputStream(), CartDTO.class);
        BookDAOMyBatis dao = new BookDAOMyBatis();
        int count = dao.addToCart(cartDTO); // 수량 , 책번호 , 고객아이디 를 가지고 즉 CartDTO타입을 가지고 >> 여부결정

        resp.setContentType("application/json");//따라서 서버가 resp.setContentType("application/json");을 설정하지 않으면 클라이언트가 응답 데이터를 어떤 형식으로 처리해야 할지 모른다
        resp.setCharacterEncoding("UTF-8");

        if(count>0){
            //적어도 하나의 행이 성공정으로 삽입이 되었을때
            resp.getWriter().write("{\"success\":true}"); //이값으로 응답을 한다
        }else{
            resp.getWriter().write("{\"success\":false}");
        }

        return null;
    }
}
