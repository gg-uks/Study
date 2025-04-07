<%@ page contentType="text/html;charset=UTF-8" language="java"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>

    <div class="container">
      <h2>Web MVC Framework</h2>
      <div class="card">
        <div class="card-header">Book View</div>
        <div class="card-body">
            <table class="table table-bordered">
                <tr>
                    <td>번호</td>
                    <td>${book.num}</td>
                </tr>
                <tr>
                    <td>제목</td>
                    <td>${book.title}</td>
                </tr>
                <tr>
                    <td>가격</td>
                    <td>${book.price}</td>
                </tr>
                <tr>
                    <td>가격</td>
                    <td>${book.author}</td>
                </tr>
                <tr>
                    <td>페이지수</td>
                    <td>${book.page}</td>
                </tr>
            </table>
            <button class="btn btn-sm btn-primary action">목록</Button>
            <button class="btn btn-sm btn-success action">수정</Button>
            <button class="btn btn-sm btn-warning action">삭제</Button>

        </div>
        <div class="card-footer">김남국</div>
      </div>
    </div>
    <form id="myForm" method="post" action="">
        <input type ="hidden" name="num" id="num" value="${book.num}"/>
    </form>
    <script>
       document.querySelector(".card-body").addEventListener("click",function(e){
        if(e.target.classList.contains("action")){  //어느 위치에서 객체 발생하였는지 액션이 포함이 되있으면
            let form=document.getElementById("myForm");
            let inputNum=document.getElementById("num");

              switch(e.target.classList[2]){
                case "btn-primary":
                    console.log("목록버튼클릭");
                    form.action="/s3/list"
                    if(inputNum){
                        inputNum.remove();
                    }
                    form.method="GET";
                    break
                case "btn-success":
                    console.log("수정버튼클릭");
                    form.action="/s3/updateGet"
                    form.method="POST";
                    break
                case "btn-warning":
                    console.log("삭제버튼클릭");
                    form.action="/s3/delete"
                    form.method="POST";
                    break

              }
              form.submit();
        }


       });

      </script>
</body>
</html>