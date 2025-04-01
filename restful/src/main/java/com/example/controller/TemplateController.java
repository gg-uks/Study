package com.example.controller;

import com.example.entity.Product;
import com.example.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TemplateController {

    @Autowired
    private TemplateService service;

    @GetMapping("/rest")
    public ResponseEntity<?> test(){ //일반적인 레스트 서비스 응답
         return new ResponseEntity<>("Restful API Test", HttpStatus.OK) ;
    } //jsp로 포워드 리다익레트로 가는게 아닌 JSON

    // REST API 명세서 제공을 해줌
    // 추가적인 기능 구현
    //GET 방식으로 저장 http://localhost:8082/restful/api/products[제품 리스트 가져오기]
/*    [
    {
        "product_number": 7,
            "product_name": "세탁기",
            "inventory": 20,
            "price": 1000000,
            "manufacturer": "삼성전자"
    }
    ]*/
    // 응답데이터의 구조 데이터도 설명을 해놔야 한다 그래야 다른 고객도 쓸수있다 -메뉴얼을 만들어줘야 한다-
    @GetMapping("/products")
    public ResponseEntity<?> list(){
        List<Product> list = service.products();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    //POST 방식으로 저장 http://localhost:8082/restful/api/products[제품등록하기]---> 성공여부 정보?
    @PostMapping("/products") //JSON :{   }
    public ResponseEntity<?>register(@RequestBody Product product){ //@RequestBody 를 해야 JSON 으로 넘어온데이터를 객체로 컨버팅한다
        service.register(product);
        return new ResponseEntity<>("success",HttpStatus.OK); //200
    }
    //DELETE http://localhost:8082/restful/api/products/{id} [특정 제품 삭제하기]
    @DeleteMapping("/products/{product_number}") //POST방식이랑 똑같다고 봐야한다 의미가 같다 파라미터가 하나 있어서 다른경로이다
    public ResponseEntity<?>deleteById(@PathVariable int product_number){
        int cnt = service.deleteById(product_number);
        return new ResponseEntity<>(cnt,HttpStatus.OK); // 성공시 1이 날라감
    }

    //GET http://localhost:8082/restful/api/products/{id} [특정 제품 가져오기]
    @GetMapping("/products/{product_number}")
    public ResponseEntity<?>getById(@PathVariable int product_number){
        Product product = service.getById(product_number);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    //PUT(POST방식이랑 같음)(업데이트방식으로생각하면된다) http://localhost:8082/restful/api/products/{id} [특정 제품 수정하기] PUT와 DELETE 는 역할방식이 달라서 같은 URL이여도 상관 없다
    @PutMapping("/products/{product_number}")
    public ResponseEntity<?> update(@PathVariable int product_number,@RequestBody Product product){  // 값이 넘어올때 그아이템에 해당 아이디 값이랑 각각의 네임값이 넘어오는걸 Product타입으로 데이터가 날라와 그타입으로 받아줘야 한다 근데 JSON 객체로 날라와서 @RequestBody를 해줘야 받을수있다
       service.update(product);
       return new ResponseEntity<>("success",HttpStatus.OK);
    }
}
