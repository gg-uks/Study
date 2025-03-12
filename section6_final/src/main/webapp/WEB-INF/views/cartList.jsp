<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
   function purchase() {
       // 구매 로직 구현. 예: 서버에 구매 요청을 보내는 코드
      // alert("구매가 성공적으로 되었습니다."); // 예시 메시지
       let customer_id = "${cus.customer_id}";

        let data={
            "customer_id" : customer_id
        };
       // 장바구니를 비우는 API 호출
       fetch('${cpath}/clearCart.do', {
           method: 'POST', // 또는 'GET' (서버 설정에 맞게)
           headers: {
               'Content-Type': 'application/json',
           },
           body: JSON.stringify(data) // 필요한 데이터 전송
       })
       .then(response => response.json())
       .then(data => {
       if(data.success){
       // 서버에서 장바구니 클리어 완료 후 UI 업데이트
        alert("구매가 성공적으로 되었습니다");
         location.href = '${cpath}/list.do';  // 장바구니 페이지로 리다이렉트하거나 다른 페이지로 이동
       }else{
            alert("장바구니비우기 실패");
       }

       })
       .catch(error => {
           console.error('Error:', error);
       });
   }

    function quantityUpdate(index){
         let quantity=document.getElementById("quantity"+index).value;
         let cartNumber=document.getElementById("cartNumber"+index).value;
         location.href="${cpath}/quantityUpdate.do?cartNumber="+cartNumber+"&quantity="+quantity;
    }
</script>
</head>
<body>

<div class="container">
    <h2>Web Database Modeling Basic</h2>
    <div class="card">
<div class="card-header text-center">
    <c:if test="${!empty cus}">
        <h4 style="color: #007bff;">${cus.customer_name}님 장바구니 리스트입니다.</h4> <!-- 색상 추가 -->
    </c:if>
</div>
<div class="card-body">
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>책 번호</th>
                <th>책이름</th>
                <th>수량</th>
                <th>가격</th>
                <th>금액</th>
                <th>취소</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cartItem" items="${cartList}" varStatus="status">
                <tr>
                    <td>${cartItem.book_num}</td>
                    <td>${cartItem.title}</td>
                    <td>
                        <input type="number" id="quantity${status.index}"
                               value="${cartItem.quantity}" min="1" class="form-control" style="width: auto;" onchange="quantityUpdate(${status.index})">
                        <input type="hidden" id="cartNumber${status.index}" value="${cartItem.cart_number}">
                     </td>
                    <td>${cartItem.price}</td>
                    <td>${cartItem.price * cartItem.quantity}</td>
                    <td>
                        <button type="button" class="btn btn-warning btn-sm"
                                onclick="location.href='${cpath}/removeFromCart.do?cart_number=${cartItem.cart_number}'">취소</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <!-- 총 금액 계산 및 표시 -->
    <c:set var="totalAmount" value="0"/>
    <c:forEach var="cartItem" items="${cartList}">
        <c:set var="totalAmount" value="${totalAmount + (cartItem.price * cartItem.quantity)}"/>
    </c:forEach>
    <div class="text-right">
    <fmt:setLocale value="ko_KR" />
        <strong>총 금액: </strong><fmt:formatNumber value="${totalAmount}" type="currency"/>
    </div>

    <!-- 다시 리스트로 가는 버튼과 구매 버튼 -->
            <div class="card-footer text-center">
                <a href="${cpath}/list.do" class="btn btn-secondary">다시 리스트로</a> <!-- 다시 리스트로 가는 버튼 -->
                <button type="button" class="btn btn-success" onclick="purchase()">구매</button> <!-- 구매 버튼 -->
            </div>

</div>
        <div class="card-footer">김남국</div>
    </div>
</div>

</body>
</html>