package com.example.controller;

import com.example.entity.CustomerDTO;
import com.example.repository.BookDAOMyBatis;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClearCartController implements Controller{
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 요청에서 JSON 데이터를 읽어오기 위해 ObjectMapper 사용
        ObjectMapper objectMapper = new ObjectMapper();

        // 요청 본문에서 전송된 JSON 데이터를 CartDTO 객체로 변환
        // 여기서 CartDTO는 customer_id만 받아오는 DTO로 설정할 수 있습니다.
        CustomerDTO customerDTO = objectMapper.readValue(req.getInputStream(), CustomerDTO.class);

        // BookDAOMyBatis를 사용하여 장바구니 비우기 작업 수행
        BookDAOMyBatis dao = new BookDAOMyBatis();
        int count = dao.clearCart(customerDTO);  // 고객 ID를 사용하여 장바구니 비우기

        // 응답 타입 설정
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // 성공 여부에 따라 응답 작성
        if (count > 0) {
            // 장바구니가 비워졌을 때 성공 응답
            resp.getWriter().write("{\"success\":true}");
        } else {
            // 실패했을 때 응답
            resp.getWriter().write("{\"success\":false}");
        }

        return null; // 리턴 값은 없으므로 null 반환
    }
}

