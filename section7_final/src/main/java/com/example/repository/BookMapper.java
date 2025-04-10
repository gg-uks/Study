package com.example.repository;

import com.example.entity.Bible;
import com.example.entity.Book;
import com.example.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper//매퍼 어노테이션은 주로 인터페이스 부분에 적음 root.context에 경로를 스캔해주면 안적어도 가능하다-내생각- 2. > 인터페이스만 메모리 위에 올려야하기때문에 매퍼 어노테이션 적음
public interface BookMapper {//인터페이스는 단독으로 사용할수 없는데 Mybatis가 구현체를 제공 해줘서 인터페이스만 있어도 사용가능
                                //구현체는 sqlsession 팩토리가 구현해주기때문에 구현체를 직접 안적어도 사용가능하다
    public List<Book> bookList();
    public void saveBook(Book dto);
    public void bibleInsert(Bible bible);
    List<Map<String, Object>> getMonthlyData(@Param("customerId") String customerId);
    //키와 벨류로 받는 이유는 키:날짜 벨류:개수로 받아야 하기때문이다
    //근데 이걸또 리스트로 묶는 이유는 여러개의 데이터를 담아야 하기때문이다.

    public Customer login(Customer customer);
    public List<Customer> customers();
}
