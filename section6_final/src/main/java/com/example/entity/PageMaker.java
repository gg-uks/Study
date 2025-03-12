package com.example.entity;

import lombok.Data;

@Data
public class PageMaker {
    private Criteria cri;// 기준이되는값 필요하다 11 12 13
    private int totalCount; // 전체게시글의수가 있어야 촢 페이지 버튼의 개수를 구할수 있음
    private int displayPageNum=3; //버튼의 개수라고 생각=> 3으로 지정해 startPage값을 정할수 있음 startPage=(endPage-displayPageNum)+1
    private int startPage; // 자기페이지 그룹에 스타트
    private int endPage; //  자기페이지 그룹에 마지막 엔드페이지는 전체페이지 개수와 비교해 전체페이지개수보다 더크면 문제가발생 이부분은 현재 마지막페이지로 교체 해줘야 한다
    private boolean prev; // 이전버튼(true, false) true 면 버튼 생성 false면 버튼 생성 X
    private boolean next; // 다음버튼(true, false) ==
    public void setTotalCount(int totalCount) { //전체게시글의 수를 만들어야 한다 DAO를 통하여 setter 에 값을 집어넣는순간 실행
        this.totalCount=totalCount;
        pageMaker();
    }
    public void pageMaker() {
        // 1.해당페이지(12)의 끝페이지번호 => 내가누른페이지가 5페이지라고 가정 =>(5/3.0)=> (1.2*...)니 ceil => 2*3=6 int로 캐스팅해서 정수로받는다고 생각하면된다 그럼 endPage구함
        endPage=(int)(Math.ceil(cri.getPage()/(double)displayPageNum)*displayPageNum); //해당그룹 페이지의 마지막 페이징 번호
        //(Math.ceil(cri.getPage()/(double)displayPageNum) =>해당그룹  *displayPageNum)=> *3 ==>해당그룹*3 == endPage

        // 2.해당페이지의 시작페이지번호
        startPage=(endPage-displayPageNum)+1; //해당그룹페이지의 첫번째 페이징 번호

        // 3.마지막페이지 번호 구하기 (전체 레코드가 22개라고 가정=> 22/3.0 => 7.***)  Math.ceil은 바로위에 최대정수(8)이 반환 int로 캐스팅 해서받음 Math.floor은 하위정수 7.*** 7이반환
        int tempEndpage=(int)(Math.ceil(totalCount/(double)cri.getPerPageNum()));
        //위 작업하는이유는 유효성 검사를 통한 endPage가 해당 마지막 페이지보다 클경우 (전체 레코드가 22개라고 가정=> 1 2 3 - 4 5 6 - 7 8 9 =>8,9페이지는 나오면 안됨 그러므로 이작업을 함)

        // 4.endPage의 유효성 검사
        if(endPage>tempEndpage) { //8>9=>
            endPage=tempEndpage; // endPage를 조정해야한다.
        }
        // 5.이전버튼의 생성여부: prev()
        prev=(startPage!=1) ? true : false; //스타트페이징 번호가 1이 아니면 이전버튼 생성 true

        // 6.다음버튼의 생성여부: next()    tempEndpage:마지막 페이지를 뜻함
        next=(endPage<tempEndpage)? true : false; //마지막 페이징번호가 마지막페이징번호 보다 작으면 버튼 생성 true 마지막 페이징번호보다 크면 false
    }
}
