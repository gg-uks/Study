package com.example.repository;
//JDBC -> MyBatis(MVC) -> Spring+Mybatis ->Hibernate ->Spring Data JPA(hibernate)

import com.example.entity.BookDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//JDBC -> 생산성이 떨어진다(시간이 많이 소요된다)java코드 + SQL : 믹서
//Java코드+SQL : 믹서구현(유지보수가 어렵다)->MyBatis :SQL Mapping Framework for Java

public class BookDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public Connection getConnect(){
        String driverClassName="com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/books";
        String username="root";
        String password = "1234";

        try{
            Class.forName(driverClassName);
            conn=DriverManager.getConnection(url,username,password);

        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    public List<BookDTO>bookList(){
        List<BookDTO> list = new ArrayList<>();
        String SQL="select * from book order by title desc";
        conn=getConnect(); //Connection 생성 -> 오버헤드 발생(시간이걸린다 ex)1초,재활용을 하자:Connection POOL)
        try {
            ps=conn.prepareStatement(SQL);
            rs=ps.executeQuery();
            while (rs.next()){
                int num=rs.getInt("num");
                String title =rs.getString("title");
                int price=rs.getInt("price");
                String author =rs.getString("author");
                int page=rs.getInt("page");
                BookDTO dto= new BookDTO(num,title,price,author,page);
//                dto.setNum(num);
                list.add(dto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                ps.close();
                conn.close();
            }catch (Exception e){
               e.printStackTrace();
            }

        }
        return list;
    }
    public int bookInsert(BookDTO dto){
        String SQL = "insert into book(title,price,author,page) values(?,?,?,?)";
        conn =getConnect();
        int cnt = -1;//실패
        try{
            ps = conn.prepareStatement(SQL);//프리컴파일
            ps.setString(1,dto.getTitle());
            ps.setInt(2,dto.getPrice());
            ps.setString(3,dto.getAuthor());
            ps.setInt(4,dto.getPage());
            cnt = ps.executeUpdate();//완성된 SQL 다시실행->저장(성공:1)    수정되는내용 업데이트 딜리트도 똑같음
        }catch (Exception e){
            e.printStackTrace();
        }
        return cnt;
    }
    //상세보기 메서드 정의
    public BookDTO getById(int reqNum){
        BookDTO dto = null;
        conn =getConnect();
        String SQL="select * from book where num=?";
        try{
            ps = conn.prepareStatement(SQL);
            ps.setInt(1,reqNum);
            rs = ps.executeQuery(); //쿼리 실행 가지고 오는 것
            if(rs.next()){
                int num = rs.getInt("num");
                String title = rs.getString("title");
                int price = rs.getInt("price");
                String author = rs.getString("author");
                int page = rs.getInt("page");
                dto=new BookDTO();
                dto.setNum(num);
                dto.setTitle(title);
                dto.setPrice(price);
                dto.setAuthor(author);
                dto.setPage(page);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }
    public void deleteById(int num){
        conn=getConnect();
        String SQL="delete from book where num=?";  //SQL

        try {
            ps=conn.prepareStatement(SQL);//프리컴파일
            ps.setInt(1,num);
            ps.executeUpdate();//성공
        }catch (Exception e){
            e.printStackTrace();//실패 (예외)
        }
    }
    //수정메서드
    public void update(int num, BookDTO dto){
        conn=getConnect();
        String SQL="update book set title=?,price=?,author=?,page=? where num=?";
        try {
            ps=conn.prepareStatement(SQL);
            ps.setString(1,dto.getTitle());
            ps.setInt(2,dto.getPrice());
            ps.setString(3,dto.getAuthor());
            ps.setInt(4,dto.getPage());
            ps.setInt(5,num);
            ps.executeUpdate(); //수정완료
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
