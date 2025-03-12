package com.example.controller;

import com.example.entity.BookDTO;
import com.example.entity.Criteria;
import com.example.entity.PageMaker;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
//http://localhost:8081/s6/list.do?page=4&perpage=3
public class BookListController  implements  Controller{ // POJO
       public String requestHandler(HttpServletRequest req, HttpServletResponse resp)
                                                           throws ServletException, IOException {

           Criteria criteria = new Criteria();   //초기화 page=1  perpageNum=3
           if(req.getParameter("page")!=null){ //페이지가 넘어오면  page라는 변수에 값을넣어 크리테리아에 set
               int page=Integer.parseInt(req.getParameter("page"));
               criteria.setPage(page);
           }
           if(req.getParameter("perPageNum")!=null){ //페이지가 넘어오면  page라는 변수에 값을넣어 크리테리아에 set
               int perPageNum=Integer.parseInt(req.getParameter("perPageNum"));
               criteria.setPerPageNum(perPageNum);
           }

        //BookDAO dao=new BookDAO();
        //Model과연동하는 부분
        BookDAOMyBatis dao=new BookDAOMyBatis();

        List<BookDTO> list=dao.bookList(criteria);
       // 객체 바인딩 기술
        req.setAttribute("list", list); // ${list}  몇개의 게시물을 나눠서 줄것인지 전달

        //PageMaker 객체 생성
           PageMaker pm = new PageMaker(); //startPage , page , endPage ,prev , next
           pm.setCri(criteria);
           pm.setTotalCount(dao.totalCount()); //토탈 게시글의 수를 저장 토탈이 있어야 다른 startPage , page , endPage ,prev , next 값이 만들어진다 (메서드호출로인한)

           req.setAttribute("pm",pm);//객체바인딩 =>가정) pm.cri.page=8 이렇게 해서 하이라이트 해주면됨 jsp단에서

        // View와 연동하는 부분
        // forward, dispatcher
        //RequestDispatcher rd=req.getRequestDispatcher("/WEB-INF/views/list.jsp");
        //rd.forward(req, resp); //-------------------------------------------| ${list}
        return "list"; //  /WEB-INF/views/list.jsp
    }
}
