package com.example.controller;

import com.example.entity.Criteria;
import com.example.entity.PageMaker;
import com.example.entity.Product;
import com.example.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // JSON
@RequestMapping("/api")
public class TemplateController {

    @Autowired
    private TemplateService service;

    @GetMapping("/rest")
    public ResponseEntity<?> test(){
         return new ResponseEntity<>("Restful API Test", HttpStatus.OK);
    }
    // [REST API 명세서]를 제공을 해준다.
    // 추가적인 기능 구현
    // GET : http://localhost:8081/restful/api/products [제품리스트 가져오기] ------> ?
    /*
            [
            {
                "product_number": 7,
                "product_name": "세탁기",
                "inventory": 20,
                "price": 1000000,
                "manufacturer": "삼성전자"
            }
        ]
     */
     @GetMapping("/products")
     public ResponseEntity<?> list(){
         List<Product> list=service.products();
         return new ResponseEntity<>(list, HttpStatus.OK);
     }

     // POST : http://localhost:8081/restful/api/products [제품등록하기] ---------> 성공(1, 0)여부 정보 ?
    @PostMapping("/products") // JSON : {         }
    public ResponseEntity<?> register(@RequestBody Product product){
          service.register(product);
          return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // DELETE :  http://localhost:8081/restful/api/products/{id} [특정제품삭제하기] ------> 1, 0
    @DeleteMapping("/products/{product_number}")
    public ResponseEntity<?> deleteById(@PathVariable int product_number ){
           int cnt=service.deleteById(product_number);
           return new ResponseEntity<>(cnt, HttpStatus.OK); // 1
    }

   // GET : http://localhost:8081/restful/api/products/{id} [특정제품 가져오기] ------> ?
    @GetMapping("/products/{product_number}")
    public ResponseEntity<?> getById(@PathVariable int product_number){
           Product product=service.getById(product_number);
           return new ResponseEntity<>(product, HttpStatus.OK);
    }


    // PUT  :  http://localhost:8081/restful/api/products/{id} :  { product_number, product_name, inventory, price }
    @PutMapping("/products/{product_number}")
    public ResponseEntity<?> update(@PathVariable int product_number, @RequestBody Product product ){
         service.update(product);
         return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/products/search")  // {"keyword":"세탁기"}
    public ResponseEntity<?> search(@RequestBody Map<String, String> data){
        // System.out.println(data.get("keyword"));
        List<Product> pList=service.search(data.get("keyword"));
        return new ResponseEntity<>(pList, HttpStatus.OK);
    }

    @PutMapping("/products/{product_number}/inventory")
    public ResponseEntity<?> updateInventory(@PathVariable int  product_number,
                                             @RequestBody Map<String, Integer> inventoryInfo){
        service.updateInventory(product_number, inventoryInfo.get("inventory")); //프론트에서 inventory라는 키값을 읽어 서비스 전달
        return new ResponseEntity<>("Inventory updated successfully", HttpStatus.OK);
    }



    @PostMapping("/products/paging") // { "page":3 , "size" : 10}  이 넘어와서 디비가공후 데이터 응답
    public ResponseEntity<?> pageList(@RequestBody Criteria cri){
        List<Product> list=service.pageList(cri); // 제품정보와 페이징번호를 넘겨서 1페이지는 10개 2페이지 10개 이런식으로 list저장
        PageMaker pm=new PageMaker();
        pm.setCri(cri); //페이지 번호  1 , 10  현재 몇 페이지인지 알아야, 어떤 버튼이 활성화(active)인지 표시 가능
        pm.setTotalCount(service.totalCount()); //전체 데이터 개수가 몇 개인지 알아야, 총 페이지 수 계산 가능
        pm.setLists(list);//몇개의 데이터를 뿌려줄건지 몇페이지에
        return new ResponseEntity<>(pm, HttpStatus.OK);
    }
//    list만 넘기면:
//    그냥 데이터만 보여줄 수 있음
//    사용자한테 페이지 버튼을 보여줄 수 없음 ❌
//    그래서 서버는:
//    데이터 (list)
//    전체 개수 (totalCount)
//    페이징 정보 (startPage, endPage, prev, next, cri.page…)
//    이 모든 걸 PageMaker 객체에 담아서 보내줌
}
