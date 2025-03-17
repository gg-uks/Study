package com.example.controller;

import com.example.entity.Book;
import com.example.repository.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // Controller 어노테이션은 @ResponseBody를 붙혀줘야 그메서드는 json데이터로 보낸다는 의미고
                // RestController은 다 json 데이터로 보내는 역할이다.
public class SpringRestController {

    @Autowired  //의존성 주입
    private BookMapper bookMapper; //객채생성과 비슷하다

    @RequestMapping("/rest")//JSON 데이터를 받으려는 컨트롤러
    public  List<String> rest(){
        List<String> list=new ArrayList<>();
        list.add("스프링");//value값
        list.add("하면");//value값
        list.add("된다");//value값
        return list; //rest.jsp <--뷰를만들면 된다(x) 왜냐 json데이터여서 fecth().then().then() 등 다양한 방식으로 응답 : json -> {key:value}
        // @ResponseBody 는 JSON데이터로 변환됨 누가? 스프링안에있는 메세지 컨버터가 바꿔준다
        // |   jackson-databind API 라고 생각하면 된다 --> implementation 'com.fasterxml.jackson.core:jackson-databind:2.13.0'

    }
    @RequestMapping("/restlist")
    public List<Book> list(){
        List<Book> list = bookMapper.bookList();
        return list;
    }
    @PostMapping("/restsave")
    public String saveBook(@RequestBody Book dto){ //json 데이터를 Book DTO에 받으려면 @RequestBody 써야함
        bookMapper.saveBook(dto);
        return "success";
    }
}
