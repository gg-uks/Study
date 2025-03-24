package com.example.controller;

import com.example.entity.Book;
import com.example.repository.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/register")
    public String registerGet(){
        return "register"; //register.jsp
    }

    @PostMapping("/register")
    public String registerPost(@RequestBody Book dto){ //폼 데이터 파라메터 수집이 자동으로 됨 서블릿은 req.getpramater 로 해야함
        bookMapper.registerBook(dto);
        return "redirect:/list";
    }
    @GetMapping("/get/{num}") // get?num=12
    public String get(@PathVariable int num ,Model model){
        Book book = bookMapper.get(num);
        model.addAttribute("book",book);
        return "get";
    }
    @GetMapping("/remove/{num}")
    public String remove(@PathVariable int num){
            bookMapper.remove(num);
        return "redirect:/list";
    }
    @GetMapping("/update/{num}")
    public String updateGet(@PathVariable int num,Model model){
        Book book = bookMapper.get(num);
        model.addAttribute("book",book);
        return "update"; //update.jsp
    }
    @PostMapping("/update")
    public String updatePost(Book dto){ //파라메터 수집
        bookMapper.update(dto);
//        return "redirect:/list";
        return "redirect:/get/"+dto.getNum(); //상세 보기페이지로 리다이렉트
    }
    @RequestMapping("/jsonList")
    public @ResponseBody List<Book> jsonList(){
        List<Book> list = bookMapper.bookList();

        return list;
    }

}
