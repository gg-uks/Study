package com.example.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MapSearchController {
    //  주소->위도, 경도
    private final String apiKey="1a4a29af7ad0c363148cc2a9190781cd"; //레스트 API키 를 말함

    @GetMapping("/search/maps")
    public ResponseEntity<String> searchMaps(String address){

        String url="https://dapi.kakao.com/v2/local/search/address.json?query="+address; //카카오톡 공식 디벨로퍼 에 나와있음 요청주소 ->쿼리 파라미터 필수인게있다 ?다음으로 나오는 쿼리스트링 필수값은 꼭 넣어줘야 한다

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);// 이헤더도 카카오 공식디벨로퍼에 제공함 어떤식으로 보내라고  -> -H "Authorization: KakaoAK ${REST_API_KEY}" \

        HttpEntity<String> entity=new HttpEntity<>(headers); //헤더를 그냥 보낼수 없고 HttpEntity<String> 서버로 보낼때는  엔티티에 집어 넣어서 보내야한다
        //근데 지금보다 헤더정보가 하나만 넣는게아니라 여러개일수도 있어서 엔티티 타입으로 보내는것이다

        RestTemplate restTemplate=new RestTemplate(); //url 주소랑 연결하기위한 클래스
        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, entity, String.class); //url정보 어떤방식으로 요청할지GET ,헤더정보 , json을 스트링 타입으로 리스폰스받겠다라는뜻

        return ResponseEntity.ok()//리턴하는 방법은 여러가지다 저번에 했던 내용으로 보내도됨
                .contentType(MediaType.APPLICATION_JSON_UTF8) //한글을 꺠지지 않게하기위한 처리
                .body(response.getBody()); // get body를 사용해 json코드만 꺼내서 보냄
    }

}