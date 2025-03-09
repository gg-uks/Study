<%@ page contentType="text/html;charset=UTF-8" language="java"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="cpath" value="${pageContext.request.contextPath}"/>
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
  <script>
     function goDel(num){
         location.href="${cpath}/delete.do?num="+num; //   ${cpath}/delete?num=1
     }
     function goRegister(){
        location.href="${cpath}/registerGet.do";
     }
  </script>
</head>
<body>

    <div class="container">
      <h2>Web MVC Framework Advanced</h2>
      <div class="card">
        <div class="card-header" style="display:flex; justify-content:center;">
           <c:if test="${empty cus}">
            <form class="form-inline" action="/SP07/login.do" method="post">
              <label for="customer_id">ID:</label>
              <input type="text" class="form-control" placeholder="Enter customer_id" id="customer_id" name="customer_id">
              <label for="password">Password:</label>
              <input type="password" class="form-control" placeholder="Enter password" id="password" name="password">
              <button type="submit" class="btn btn-primary btn-sm">로그인</button>
            </form>
           </c:if>
           <c:if test="${!empty cus}">
            <form class="form-inline" action="/SP07/logout.do" method="post">
                  <label>${cus.customer_name} 님!!.[적립금]:${cus.reserves} </label>
                  <button type="submit" class="btn btn-primary btn-sm"> 로그아웃</button>
            </form>
           </c:if>
        </div>
        <div class="card-body">
          <table class="table table-bordered table-hover">
              <thead>
                  <tr>
                     <th>이미지</th>
                     <th>번호</th>
                     <th>제목</th>
                     <th>가격</th>
                     <th>저자</th>
                     <th>페이지수</th>
                     <th>삭제</th>
                     <th>이미지등록</th>
                  </tr>
              </thead>
              <tbody>
              <c:forEach var="book" items="${list}">
                                  <tr>
                                       <c:if test="${!empty  book.path}">
                                            <td><img  src="${cpath}/download.do?book_num=${book.book_num}&path=${book.path}" style="height:100px;width:80px"/></td>
                                       </c:if>
                                       <c:if test="${empty  book.path}">
                                             <td>이미지를 등록하세요</td>
                                       </c:if>
                                       <td>${book.num}</td>
                                       <td><a href="${cpath}/view.do?num=${book.num}">${book.title}</a></td>
                                       <td>${book.price}</td>
                                       <td>${book.author}</td>
                                       <td>${book.page}</td>
                                       <td>
                                       <c:if test="${!empty cus}">
                                        <button class="btn btn-sm btn-warning" onclick="goDel(${book.num})">삭제</button>
                                       </c:if>
                                       <c:if test="${empty cus}">
                                         <button disabled class="btn btn-sm btn-warning" onclick="goDel(${book.num})">삭제</button>
                                       </c:if>
                                       </td>
                                       <td><a class="btn btn-sm btn-info" href="${cpath}/imageGet.do?book_num=${book.num}">이미지등록</a></td>
                                    </tr>
              </c:forEach>
              </tbody>
          </table>
          <c:if test="${!empty cus}">
          <button class="btn btn-sm btn-primary"  onclick="goRegister()">등록하기</button>
          </c:if>
        </div>
        <div class="card-footer">김남국</div>
      </div>
    </div>

</body>
</html>