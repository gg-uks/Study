package com.example.controller;

import com.example.entity.Book;
import com.example.repository.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller//서블릿에서는 클래스를 의미 스프링에서는 이 컨트롤러가 모든걸 리스폰스
public class SpringController {

    @Autowired
    private BookMapper bookMapper;

    @RequestMapping("/spring")//서블릿에서는 경로지정 이랑 비슷하다고 생각하면된다
    public String index(){
        return "template";
    }
    @RequestMapping("/list")
    public String list(Model model){
        List<Book> list = bookMapper.bookList();
        model.addAttribute("list",list);
        return "list";
    }
}
