package com.example.repository;

import com.example.entity.BookDTO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class BookDAOMybatis {// 리팩토링=>중앙집중식 관리,코드의 간결성,자원관리의 효율성

    public List<BookDTO> bookList(){
       try(SqlSession session=MybatisUtil.openSession()){ // 자동 close()
           return session.selectList("bookList");

       }
        //try 블록이 종료 되면 session은 자동으로 close 됩니다


    }
}
