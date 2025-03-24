package com.example.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class KyoboController {
    @GetMapping("/kysearch")
        public ResponseEntity<?> kysearch(@RequestParam("title") String title) {

        String url ="https://search.kyobobook.co.kr/search?keyword="+title; //교보문고의 주소

        RestTemplate restTemplate = new RestTemplate(); //url을 요청하려면 스프링에서 제공해주는 레스트템플릿 클래스를 써야함
        String response = restTemplate.getForObject(url,String.class);//.getForObject 어떤 url로 요청해서 어떤데이터를 받을거냐
        System.out.println(response); //출력이되는부분 HTML코드가 보여짐

        //서버가 클라이언트에게 응답시 정보를 알려주는 행위를해야함 (Header정보를 줘여함)
        HttpHeaders headers = new HttpHeaders(); //스프링에서 제공해주는 HttpHeaders를 사용해서 한글인코딩을 해야함
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);//응답하려는 데이터를 utf-8 로 인코딩을 하면 받는 클라이언트쪽에서 utf-8로 데이터를 받아 한글이 안깨짐

        return new ResponseEntity<>(response,headers, HttpStatus.OK); //응답값 , 헤더에 인코딩된값 , 성공유무에 대한 상태값을 넘겨줌

    }
}
