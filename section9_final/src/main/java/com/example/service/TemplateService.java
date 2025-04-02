package com.example.service;

import com.example.entity.Criteria;
import com.example.entity.Product;
import com.example.repository.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {
     @Autowired
    private TemplateMapper mapper;

    // 추가적인 기능 구현
   public List<Product> products(){
         return mapper.products();
   }

   //해당 페이지의 해당하는 제품의 개수를 가져오기(page=2 size=10)
   public List<Product> getPagingList(Criteria cri){
       return mapper.getPagingList(cri);
   }
   //전체 레코드의 수를 구하는 메서드
    public int totalCount(){
       return mapper.totalCount();
    }

   public void register(Product product){
          mapper.register(product);
   }

   public int deleteById(int product_number){
         return mapper.deleteById(product_number);
   }

   public Product getById(int product_number){
        // 코딩~~~
         return mapper.getById(product_number);
   }

   public void update(int product_number,Product product){
       // 코딩~~~
        mapper.update(product_number,product);
   }
   public List<Product> search(String keyword){
       return mapper.search(keyword);
   }

    public void updateinventory(int productNumber, int inventory) {
       mapper.updateinventory(productNumber,inventory);
    }
}
