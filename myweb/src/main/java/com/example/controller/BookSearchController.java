package com.example.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;

@RestController
public class BookSearchController {
    private final String apikey="내 API키 "; //책검색 apikey

    @GetMapping("/search/books")//Get방식맵핑    title="인공지능"
    public ResponseEntity<String> searchBooks(String title){
        //kakao booksearch open api url주소
        final String url="https://dapi.kakao.com/v3/search/book?query="+title;
        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization" , "KakaoAK " + apikey);
        //Authorization → HTTP 요청 헤더의 이름 (인증 정보 전달)
        //"KakaoAK " → 카카오 API에서 사용하는 인증 유형
        //KakaoAK는 **"Kakao Application Key"**의 약자
        //카카오 API는 Authorization 헤더에 "KakaoAK {REST_API_KEY}" 형식으로 인증 정보 전달해야 함
        //apikey → 실제 REST API 키 값


        //headers.setContentType(MediaType.APPLICATION_JSON_UTF8); //값을 지금은 하나만 받아서 json데이터를 받을떄 쓰인다
        HttpEntity<String> entity=new HttpEntity<>(headers);//헤더정보를 보내려면 이렇게 타입지정해서 entity 로 보내줘야 한다

        RestTemplate restTemplate = new RestTemplate(); //final String url="https://dapi.kakao.com/v3/search/book?query="+title; 이쪽 서버와 연결해 데이터를 보내는역할
        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, entity, String.class);//해당 url로 GET방식으로 요청하고 , 헤더값이 전달이되고 , 전달된결과값이 스트링으로 보내는것이다
//  ResponseEntity<String> 타입으로 리스폰스된다 이안에는 성공요청까지 포함되있다.




                    //200이넘어감
        return ResponseEntity.ok() //성공적으로 된다면
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response.getBody()); //넘겨야하는데이터는 바디에 있음


    }
}
