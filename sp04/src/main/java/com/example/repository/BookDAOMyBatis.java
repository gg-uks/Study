package com.example.repository;

import com.example.entity.BookDTO;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDAOMyBatis { // 리팩토링 => 중앙집중식 관리, 코드의 간결성, 자원의관리의 효율성

        public List<BookDTO> bookList(){
            try(SqlSession session=MyBatisUtil.openSession()){ // close()
                 return session.selectList("com.example.repository.BookDAOMyBatis.bookList");
            }
        // try 블록이 종료되면 session은 자동으로 close 됩니다.
       }

    public void deleteById(int num) {
        try(SqlSession session=MyBatisUtil.openSession()){ // close()
            int cnt = session.delete("deleteById",num);
            session.commit(); //완료
        }
    }

    public BookDTO getById(int num) {
        try(SqlSession session=MyBatisUtil.openSession()){ // close()
            return session.selectOne("getById",num);
        }
    }

    public int bookInsert(BookDTO dto) {
        try(SqlSession session=MyBatisUtil.openSession()){ // close()
           int cnt = session.insert("bookInsert",dto);
                 session.commit();
            return cnt;
        }
    }

    public void update(int num, BookDTO dto) {
        try(SqlSession session=MyBatisUtil.openSession()){ // close()

            Map<String,Object> paramMap = new HashMap<>(); //매개변수 두개를 전달하기위한 맵으로 담아서 활용 -중요하다-
            paramMap.put("num",num);
            paramMap.put("dto",dto);

            int cnt = session.update("update",paramMap);
            session.commit();
        }

    }
}
