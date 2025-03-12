package com.example.controller;

import com.example.entity.CartBookDTO;
import com.example.entity.CustomerDTO;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CartListController implements Controller{
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //로그인한 사용자 id customer_id
        HttpSession session = req.getSession();
        CustomerDTO dto = (CustomerDTO)session.getAttribute("cus");
        String customer_id = dto.getCustomer_id();

        BookDAOMyBatis dao = new BookDAOMyBatis();
        List<CartBookDTO> cartList = dao.cartBookList(customer_id);// cart테이블과 book테이블을 조인해서 정보를 가져와야한다

        req.setAttribute("cartList",cartList);
        return "cartList";
    }
}
