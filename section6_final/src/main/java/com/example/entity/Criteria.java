package com.example.entity;

import lombok.Data;

@Data
public class Criteria { //기준
    private int page; // 현재페이지 4, 5
    private int perPageNum; //한페이지에 몇개의 게시물을 보여줄것인가 10

    public Criteria() { //생성될때 초기화 되는 값
        this.page=1; //맨처음에 1페이지가 로딩
        this.perPageNum=3; //게시물은 3개 보이기 // ?
    }

    // 시작번호를 구하는메서드: limit ,
    public int getPageStart() { //기준이되는 페이지 시작번호를 가져오는 메서드 , DAO에서 사용할때 pageStart 로 getter호출가능하다
        //1page->0~9 , 2page->10~19, 3page->20~29
        return (page-1)*perPageNum;
    }
}
