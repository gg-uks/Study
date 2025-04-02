package com.example.repository;

import com.example.entity.Criteria;
import com.example.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateMapper {

    // 추가적인 기능 구현
    public List<Product> products();
    public void register(Product product);
    public int deleteById(int product_number);
    public Product getById(int product_number);
    public void update(@Param("product_number") int product_number, @Param("product") Product product);
    public List<Product> search(String keyword);
    public void updateinventory(@Param("productNumber") int productNumber,
                                @Param("inventory") int inventory);
    //페이징 처리에서 필요한 메서드
    public List<Product> getPagingList(Criteria cri);
    public int totalCount();
}

