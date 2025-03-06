<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="cpath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Image Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- 삭제 버튼 스타일 -->
<style>
.img-container {
    position: relative;
    display: inline-block; /* 또는 필요에 따라 다른 display 속성 사용 */
    margin: 10px; /* 컨테이너 외부 여백을 추가하여 이미지들 사이의 간격을 조정 */
}

.img-fluid {
    max-width: 100%; /* 이미지의 최대 너비를 컨테이너의 너비로 제한 */
    height: auto; /* 이미지의 높이를 자동으로 조정하여 종횡비를 유지 */
}

.delete-btn {
    position: absolute;
    top: 5px; /* 상단에서의 위치 */
    right: 5px; /* 우측에서의 위치 */
    /* 기타 스타일링 속성 */
}
</style>

</head>
<body>

<div class="container">
    <h2>Web MVC Framework Advanced</h2>
    <div class="card">
        <div class="card-header">Upload Images</div>
        <div class="card-body">

      <h2 class="text-center">책 이미지 등록</h2>
      <div class="mb-3 row">
        <label class="col-md-3 col-form-label">책번호</label>
        <div id="book_num" class="col-md-9">
           ${book.num}
        </div>
      </div>
      <div class="mb-3 row">
        <label class="col-md-3 col-form-label">책제목</label>
        <div class="col-md-9">
           ${book.title}
        </div>
      </div>
      <div class="mb-3 row">
        <label class="col-md-3 col-form-label">쎔네일이미지</label>
        <div class="col-md-9">
         <div class="row">
            <c:forEach var="img" items="${mlist}">
             <c:if test="${img.type==1}">
	             <div class="col-lg-3 col-md-4 col-sm-2">
	               <div class="img-container">
	                <img src="${cpath}/download.do?book_num=${img.book_num}&path=${img.path}" class="img-fluid" />
	                <div class="delete-btn" style="cursor:pointer;" onclick="deleteImage(${img.id})">X</div>
	               </div>
	             </div>
             </c:if>
            </c:forEach>
          </div>
          <input data-type="1" class="fileInput" class="form-control"  type="file" name="files" accept="image/png,image/jpeg" multiple>
          <div class="alert alert-secondary" role="alert">
            <ul>
               <li>이미지 사이즈 : 350*350</li>
               <li>파일 사이즈 : 1M 이하</li>
               <li>파일 확장자 : png, jpg만 가능</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="mb-3 row">
        <label class="col-md-3 col-form-label">책 이미지</label>
        <div class="col-md-9">
          <div class="row">
            <c:forEach var="img" items="${mlist}">
             <c:if test="${img.type==2}">
	             <div class="col-lg-3 col-md-4 col-sm-2">
	               <div class="img-container">
	                <img src="${cpath}/download.do?book_num=${img.book_num}&path=${img.path}" class="img-fluid" />
	                <div class="delete-btn" style="cursor:pointer;" onclick="deleteImage(${img.id})">X</div>
	               </div>
	             </div>
             </c:if>
            </c:forEach>
          </div>
          <input data-type="2" class="fileInput" class="form-control" type="file" accept="image/png,image/jpeg" multiple>
          <div class="alert alert-secondary" role="alert">
            <ul>
               <li>최대 5개 가능</li>
               <li>이미지 사이즈 : 700*700</li>
               <li>파일 사이즈 : 1M 이하</li>
               <li>파일 확장자 : png, jpg만 가능</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="mb-3 row">
        <label class="col-md-3 col-form-label">책 설명 이미지</label>
        <div class="col-md-9">
          <div class="row">
             <div class="col-lg-6 col-md-8">
              <input data-type="3" class="fileInput" class="form-control" type="file" accept="image/png,image/jpeg">
               <div class="alert alert-secondary" role="alert">
               <ul>
                 <li>파일 사이즈 : 5M 이하</li>
                 <li>파일 확장자 : png, jpg만 가능</li>
               </ul>
             </div>
            </div>
            <c:forEach var="img" items="${mlist}">
             <c:if test="${img.type==3}">
	             <div class="col-lg-6 col-md-4">
	               <div class="img-container">
	                <img src="${cpath}/download.do?book_num=${img.book_num}&path=${img.path}" class="img-fluid" />
	                <div class="delete-btn" style="cursor:pointer;" onclick="deleteImage(${img.id})">X</div>
	               </div>
	             </div>
             </c:if>
            </c:forEach>
          </div>
        </div>
      </div>
       <div class="mb-3 row m-auto">
        <button type="button" class="btn btn-sm btn-dark" onclick="location.href='${cpath}/list.do'">목록으로</button>
      </div>

        </div>
        <div class="card-footer">김남국</div>
    </div>
</div>

<script>
        document.addEventListener("DOMContentLoaded", function() {
            // "fileInput" 클래스를 가진 모든 요소에 대해 이벤트 리스너 등록
            var fileInputs = document.querySelectorAll(".fileInput");

            fileInputs.forEach(function(input) { //여러 개의 .fileInput을 하나씩 input 변수에 담아 반복 실행.
                input.addEventListener("change", function(e) {
                    var files = e.target.files;  //e.target은 이벤트가 발생한 대상 요소를 참조합니다. 여기서는 input 요소, 즉 파일을 선택한 <input type="file"> 요소입니다.
                    if (files && files.length > 0) {//사용자가 하나 이상의 파일을 선택했을 경우에만 파일 업로드를 진행합니다. files.length > 0 조건으로 선택된 파일이 하나라도 있는지 확인합니다.
                        var formData = new FormData();  //FormData는 폼 데이터를 쉽게 처리할 수 있도록 도와주는 객체입니다. 이 객체는 폼 데이터를 key-value 쌍으로 저장하고, 서버로 전송할 준비를 합니다.
                                                        //  formData는 업로드할 파일 데이터를 담기 위한 객체로 초기화됩니다.
                        for (var i = 0; i < files.length; i++) {
                            formData.append("files", files[i]); //각 파일은 files[i]로 하나씩 가져와서 formData에 "files"라는 키로 추가됩니다. 여러 파일을 업로드할 때, formData는 각 파일을 "files"라는 이름으로 전송하게 됩니다.
                        }
                        // `data-type` 속성을 사용하여 type 값을 가져옵니다.
                        var type = input.getAttribute("data-type");

                        // formData와 type을 사용하여 업로드 함수 호출
                        uploadFiles(formData, type);
                    }
                });
            });
        });

        function uploadFiles(formData, type) {
            var book_num = document.getElementById("book_num").innerText.trim();
            console.log("book_num",book_num);
            formData.append('book_num', book_num);  // 예시::formData.append("book_num", "12345");
            formData.append('type', type);

        //비동기전송
         fetch('upload.do', {
             method: 'POST',
             body: formData,
         })
         .then(response => response.json()) // 서버로부터 받은 응답을 JSON으로 파싱
         .then(data => {
             if(data.status === "success") {
                 alert(data.message); // 성공 메시지 표시
                 window.location.href = "${cpath}/imageGet.do?book_num=" + parseInt(book_num); // 페이지 리다이렉션
             } else {
                 // 처리 실패에 대한 적절한 사용자 피드백
                 alert("Upload failed");
             }
         })
         .catch(error => {
             console.error('Error:', error);
             alert("Error uploading file");
         });
        }

    function deleteImage(id) {
        var book_num = document.getElementById("book_num").innerText;
        fetch('imageDelete.do?id=' + id, {
            method: 'GET'
        })
        .then(response => {
            if (response.ok) {
                alert("Deleted");
                window.location.href = "${cpath}/imageGet.do?book_num=" + parseInt(book_num);
            } else {
                return Promise.reject('Failed to delete');
            }
        })
        .catch(error => {
            alert(error);
        });
    }

</script>

</body>
</html>