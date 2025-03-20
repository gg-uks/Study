package com.example.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BookSearchController {
    private final String apiKey="1957999d280a4fa14ca7196d3b61ec30";

    @GetMapping("/search/books") // title="인공지능"
    public ResponseEntity<String> searchBooks(String title){

        // kakak Book Search Open API URL
        final String url="https://dapi.kakao.com/v3/search/book?query="+title;

        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization" , "KakaoAK " + apiKey);
        //headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity=new HttpEntity<>(headers);

        RestTemplate restTemplate=new RestTemplate(); //카카오 쪽에 요청 하려면 이 객체를 써야함 종류는 여러가지있음
        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, entity, String.class);//url | method|GET방식 |entity 헤더정보 |String.class문자열로 받겠다라는뜻
//      그래서 ResponseEntity<String> response 이쪽으로 응답을 받아 뽑아내야 해서
        return ResponseEntity.ok()//ResponseEntity.ok()뽑아내는 작업 ok성공 이면
                .contentType(MediaType.APPLICATION_JSON_UTF8)//.contentType 을 json형태로 보냄
                .body(response.getBody());//response.getBody 바디로 빼서 보낸다    json으로 날라감
    }
}
