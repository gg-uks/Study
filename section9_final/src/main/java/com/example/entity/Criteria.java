package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Criteria {
    private int page;
    private int size;
    public Criteria() {
        this.page=1;
        this.size=10;
    }
    //getPageStart() 메서드: 데이터베이스에서 조회할 때, 어디서부터 가져올지 결정하는 역할
    //
    //예를 들어, LIMIT과 OFFSET을 사용할 때 OFFSET 값으로 활용할 수 있음 >> getPageStart() 마이바티스 쿼리 실행할때 호출하여 반환한 값을 바로 매핑시킴
    public int getPageStart() {
        return (page-1)*size; //1page->0~9 , 2page->10~19, 3page->20~29
    }
}