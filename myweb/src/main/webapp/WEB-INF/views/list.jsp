<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="cpath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    //저장버튼 클릭시 처리하는 부분
    function goSave(isbn){
        let authorElement = document.getElementById("a"+isbn);
        let author = authorElement.textContent; //또는 authorsElement.innerText;사용가능 //그안에 있는 텍스트 컨텐츠 갖고오기

        let titleElement = document.getElementById("t"+isbn);
        let title = titleElement.textContent; //또는 authorsElement.innerText;사용가능 //그안에 있는 텍스트 컨텐츠 갖고오기

        let priceElement = document.getElementById("p"+isbn);
        let price = priceElement.textContent; //또는 authorsElement.innerText;사용가능 //그안에 있는 텍스트 컨텐츠 갖고오기

        //let publisherElement = document.getElementById("p"+isbn); // 출판사는 DTO에 없어서 가져올 필요 없음
        //let publisher = authorsElement.textContent; //또는 authorsElement.innerText;사용가능 //그안에 있는 텍스트 컨텐츠 갖고오기
        //alert(authors);
        console.log(author);
        console.log(title);
        console.log(price);
        //fetch().then().then()
        const url ="${cpath}/restsave";
        fetch(url,{
            method:"POST",
            headers:{
                "Content-Type" : "application/json",
            },
            body:JSON.stringify({title,price,author}) // 데이터를 JSON 문자열로 변환
        })
        .then(response => response.text()) // 서버의 응답을 JSON 형식으로 변환
        .then(data=>{
            console.log(data); //json 데이터
            location.href="${cpath}/list";
        })
        .catch(error=>{
            console.log(error);
        });
    }


    function showTitle(button){
        let row = button.closest("tr"); //button 요소에서 가장 가까운 상위 요소 중에서 "tr" 태그를 찾는다.
        let title = row.querySelector("td:nth-child(2)").textContent;
        document.getElementById("searchTitle").textContent="책 제목:"+title;
        //$.ajax(); 제이쿼리 라이브러리 써야함
        // fetch().then().then();
        let url ="${cpath}/search/books?title="+encodeURIComponent(title); //인코딩작업
        //alert(url);
        fetch(url)
         .then(response=>{ //컨트롤러로 간 요청에 응답하여 받은 json데이터를 then으로 받음
                       if(response.ok){ //컨트롤러 단에 ResponseEntity.ok()
                           return response.json(); //json 데이터 포맷으로 리턴한다
                       }
                        throw new Error("error"); //ok가 아니면 에러
                    })
                 .then(data=>{                         // response.json();  데이터를 리턴한값을 then으로  받음
                     console.log(data);  // 책목록(JSON)
                     const resultsContainer = document.querySelector('#bookList'); // div
                      resultsContainer.innerHTML = ''; // 이전 검색 결과를 클리어
                     data.documents.forEach(book=>{   //data값을 참조하여 book라는 콜백함수로 데이터정보뿌림
                         const { authors, title, price, isbn, publisher, thumbnail,  url} = book;  //book 객체 안에 있는 키값을 꺼내서 변수로 할당
                         // HTML 요소 생성 및 설정
                         const bookInfo = document.createElement('div');
                         bookInfo.classList.add("book-info");
                         bookInfo.style.border = '1px solid #ddd';
                         bookInfo.style.margin = '10px 0';
                         bookInfo.style.padding = '10px';
                         var html="<table class='table'>";
                         html+="<tr>";
                         html += "<td>저자 <button type='button' class='btn btn-sm btn-success' onclick='goSave(\"" + isbn + "\")'>저장</button></td>"; //  > \역슬래시를 해준이유는 순수하게 쌍따움표를 붙혀 문자열로 넘겨주기 위해 해준것이다
                         html += "<td id='a" + isbn + "'>" + authors + "</td>";
                         html+="</tr>";
                         html+="<tr>";
                         html+="<td>제목</td>";
                         html+="<td id='t"+isbn+"'><a href="+url+">"+title+"</a></td>";
                         html+="</tr>";
                         html+="<tr>";
                         html+="<td>가격</td>";
                         html+="<td id='p"+isbn+"'>"+price+"</td>";
                         html+="</tr>";
                         html+="<tr>";
                         html+="<td>출판사</td>";
                         html+="<td id='pu"+isbn+"'>"+publisher+"</td>";
                         html+="</tr>";
                         html+="<tr>";
                         html+="<td colspan='2'><img src="+thumbnail+"/></td>";
                         html+="</tr>";
                         html+="</table>";
                         bookInfo.innerHTML=html;
                         //bookInfo.innerHTML=price+":"+isbn+":"+publisher+":"+"<a href='"+url+"'>"+title+"</a><img src='"+thumbnail+"'/>";
                        // 생성된 요소를 컨테이너에 추가
                         resultsContainer.appendChild(bookInfo);
                     });
                   })
                   .catch(error=>{ //무슨에러가나면 catch가 처리
                     console.log(error);
                  });
              }

  </script>
</head>
<body>

<div class="container-fluid mt-3">
  <h1>Java Spring Full Stack Developer</h1>
  <p>Resize the browser window to see the effect.</p>
  <p>The first, second and third row will automatically stack on top of each other when the screen is less than 576px wide.</p>

  <div class="container-fluid">

     <div class="card">
        <div class="card-header">Java Spring Framework</div>
        <div class="card-body">
            <div class="row">
              <div class="col-sm-2 mb-2">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title">Left</h4>
                    <p class="card-text">Some example text. Some example text.</p>
                    <a href="#" class="card-link">Card link</a>
                    <a href="#" class="card-link">Another link</a>
                  </div>
                </div>
              </div>
              <div class="col-sm-7 mb-2">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title">book list</h4>
                    <p class="card-text">책리스트보여주기</p>
                     <table class="table table-bordered table-hover">
                        <thead>
                          <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>가격</th>
                            <th>저자</th>
                            <th>페이지수</th>
                            <th>검색</th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach var="book" items="${list}">
                            <tr>
                                <td>${book.num}</td>
                                <td>${book.title}</td>
                                <td>${book.price}</td>
                                <td>${book.author}</td>
                                <td>${book.page}</td>
                                <td><button class="btn btn-sm btn-info" onclick="showTitle(this)">검색</button></td>
                            </tr>
                          </c:forEach>
                        </table>
                     </tbody>
                  </div>
                </div>
              </div>
              <div class="col-sm-3">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title">검색결과</h4>
                    <p class="card-text" id="searchTitle">Some example text. Some example text.</p>
                    <div id="bookList"></div>
                  </div>
                </div>
              </div>
            </div>
        </div>
        <div class="card-footer text-center">Java Spring Full Stack Developer(김남국)</div>
     </div>

  </div>
</div>

</body>
</html>