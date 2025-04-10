package com.example.controller;

import com.example.entity.Book;
import com.example.entity.Customer;
import com.example.repository.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


//@RequiredArgsConstructor  >>생성자 주입  오토와이어트 밑에 private final BookMapper mapper; 키워드를 붙인후 사용해야함
//생성자 주입 설명
//@Controller
//public class SpringController {
//                      private final BookMapper mapper
//                      public SpringController(BookMapper mapper){
//                          this.mapper=mapper;
//                      {
//                          >>>> 이게 @RequiredArgsConstructor 이걸 뜻한다

@Controller
public class SpringController {

    @Autowired //생성자 자동주입 이라해도되고 오토와이어트라고 해도됌
    private BookMapper mapper;

    @RequestMapping("/spring")
    public String index(){
        return "template"; // template.jsp
    }

    @RequestMapping("/list")
    public String list(Model model){
        List<Book> list=mapper.bookList();
        model.addAttribute("list", list);
        return "list"; // list.jsp
    }
    @RequestMapping("/bible")
    public String bible(){
        return "bible";
    }

    @PostMapping("/login")
    public String login(Customer customer, HttpSession session){
            Customer cus = mapper.login(customer);
            if(cus != null){//성공하면 - >session
                session.setAttribute("cus",cus);
            }
        return "redirect:/bible";
    }
    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/bible";
    }
    @GetMapping("/grid")
    public String grid(){
        return "grid";
    }


}
