package com.example.repository;

import com.example.entity.BookDTO;
import com.example.entity.ReviewDTO;
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

       public List<BookDTO> bookListSort(String key){
        try(SqlSession session=MyBatisUtil.openSession()){ // close()
            return session.selectList("bookListSort",key);
        }
        // try 블록이 종료되면 session은 자동으로 close 됩니다.
    }
    //검색기능
    public List<BookDTO> bookListSearch(String keyword) {
        try(SqlSession session=MyBatisUtil.openSession()){ // close()
            return session.selectList("bookListSearch",keyword);
        }
    }

       public int bookDelete(int num){
             try(SqlSession session=MyBatisUtil.openSession()){
                   int cnt=session.delete("bookDelete", num);
                   session.commit(); // 완료
                   return cnt;
             }
       }

       public int bookInsert(BookDTO dto){
           try(SqlSession session=MyBatisUtil.openSession()){
               int cnt=session.insert("bookInsert", dto);
               session.commit(); // 완료
               return cnt;
           }
       }

       public BookDTO bookView(int num){
           try(SqlSession session=MyBatisUtil.openSession()){
               BookDTO dto=session.selectOne("bookView", num);
               return dto;
           }
       }

    public int bookUpdate(Long num, BookDTO dto) {
        try(SqlSession session=MyBatisUtil.openSession()){
            Map<String,Object> maps = new HashMap<>();
            maps.put("num",num);
            maps.put("dto",dto);
            int cnt=session.update("bookUpdate", maps);
            session.commit(); // 완료
            return cnt;
        }
    }

    //리뷰 작성
    public int reviewAdd(ReviewDTO dto){
        try(SqlSession session=MyBatisUtil.openSession()){
            int cnt=session.insert("reviewAdd", dto);
            session.commit(); // 완료
            return cnt;
        }
    }

    public List<ReviewDTO> getByNumReviews(int book_num){
        try(SqlSession session=MyBatisUtil.openSession()){
            return session.selectList("getByNumReviews",book_num);
        }
    }

    public double getAVGRating(int book_num) {
        try (SqlSession session = MyBatisUtil.openSession()) {
            Double avgRating = session.selectOne("getAVGRating", book_num);
            return (avgRating != null) ? avgRating : 0.0; // `null`이면 기본값 0.0 반환
        }
    }

    public int reviewRemove(int review_id){
        try(SqlSession session=MyBatisUtil.openSession()){
            int cnt=session.delete("reviewRemove", review_id);
            session.commit(); // 완료
            return cnt;
        }
    }


}
