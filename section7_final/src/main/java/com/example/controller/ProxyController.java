package com.example.controller;

//import com.example.entity.Bible;
import com.example.entity.Bible;
import com.example.repository.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
public class ProxyController {

    @Autowired
    private BookMapper mapper;
    //               key         value
    // JSON : {date: selectedDate}
    @PostMapping("/proxy/date")                         // JSON(@RequestBody)
    public ResponseEntity<String> getDateData(@RequestBody Map<String, String> dateData) { //vo로 받아도 된다 하나밖에 없어서 데이터가 그래서 맵으로 받은것이다 여러개의 정보를 받을때는 VO로 받으면됨
        String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter"; //웹크롤링 주소 => 클라이언트에서 선택 한 값을 넘겨야함 dateData
//        String url = "https://sports.news.naver.com/index";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();//서버에 요청하기위한 헤더 설정
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); //폼형식으로 서버에 넘기겠다라는 뜻

        // 폼 데이터 구성
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();//여기에 있는 값들은 크롤링한 주소에 있는 키와 벨류값이다
        map.add("qt_ty", "QT1");                                        //확인을 해야한다.
        map.add("Base_de", dateData.get("date")); //json데이터로 날라온 키값 -> 날짜 클라이언트에서 선택한 날짜
        map.add("bibleType", "1");//이렇게 3개를 넘겨줘야 요청한 사이트에서 결과를 전달해주니까 이렇게 3개를 넘겨야 한다

        // HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers); //서버에 정보를 넘기려고 map정보와 headers정보를 넘긴다

        // RestTemplate을 사용하여 POST 요청 전송
        RestTemplate restTemplate = new RestTemplate(); //네트워크 API다 서버와 통신하게 해주는 이게 중요한 키워드다

        // UTF-8 인코딩을 사용하는 StringHttpMessageConverter 추가
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter()); //넘기는 데이터에 한글데이터가 깨지지않기 위해 추가해줌

        // 요청 및 응답 처리
        byte[] responseBytes = restTemplate.postForObject(url, requestEntity, byte[].class); // 이게 post방식으로 요청하는 함수다 url주소, 요청할때 넘어가는값들, 서버에서 날라온 데이터를 바이트로 받겠다라는뜻
        String response = new String(responseBytes, StandardCharsets.UTF_8);//이부분은 응답 받은 데이터 html구조가 담겨있다 byte배열안에 스트링으로 바꿔서 utf-8로 바꿔 response변수애 담는다

        //HttpHeaders는 HTTP 응답의 헤더 값을 설정할 때 사용하는 클래스야.
        //→ responseHeaders에 새로운 HttpHeaders 객체를 생성하고 응답 헤더를 설정할 준비를 함.
        HttpHeaders responseHeaders = new HttpHeaders();

        //responseHeaders.setContentType()은 응답의 Content-Type을 설정해.
        //
        //"text", "html" → Content-Type: text/html 설정 (HTML 응답이라는 의미)
        //StandardCharsets.UTF_8 → 문자 인코딩을 UTF-8로 설정
        responseHeaders.setContentType(new MediaType("text", "html", StandardCharsets.UTF_8));

        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);// response=html정보  responseHeaders=헤더정보 , ok정보 200
        //리스폰스 , 헤더의 컨텐츠타입  ,응답의 성공정보
    }

    @PostMapping("/proxy/insertBible")
    public ResponseEntity<?> insertBible(@RequestBody Bible bible) {
        try {
            //bibleReflectionService.saveReflection(bibleReflection);\
            mapper.bibleInsert(bible);
            return ResponseEntity.ok().body("{\"success\": true}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}");
        }
    }
    // 추가
    @GetMapping("/proxy/monthly-data")
    public ResponseEntity<?> getMonthlyData(@RequestParam("customer_id") String customerId) {
        List<Map<String, Object>> data = mapper.getMonthlyData(customerId); //여러사람의 정보를 담기위해서 list<Map<String,Object>> 이타입으로 받아야 한다
        return ResponseEntity.ok(data);                                     //Map<String,Object>는 한사람의 정보 를 가져올때
                                                                            //List<Map<String, Object>> 이렇게 사용하는이유는 DTO가 없어도 마이바티스에서 동적으로 쿼리를 사용해 불러올수 있다
    }
}