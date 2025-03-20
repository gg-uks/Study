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
  <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script> <!-- Kakao JavaScript SDK 로드 -->
  <script src="https://uicdn.toast.com/chart/latest/toastui-chart.min.js"></script> <!-- Toast UI Chart JavaScript SDK 로드 -->

<style>
  /* 커스텀 스타일 */
  .custom-container {
    padding-right: 15px;
    padding-left: 15px;
  }

  /* 화면 크기에 따라 여백 조정 */
  @media (max-width: 768px) {
    .custom-container {
      padding-right: 5px;
      padding-left: 5px;
    }
  }

  /* 기타 스타일은 동일하게 유지 */
  .card {
    background-color: #f8f9fa;
    border: none;
  }
  .card-header, .card-footer {
    background-color: #007bff;
    color: #ffffff;
  }
  .btn-primary {
    background-color: #28a745;
  }
  .btn-secondary {
    background-color: #6c757d;
  }
  .dotted-line {
    border-bottom: 1px dotted #000;
    padding-bottom: 10px;
    margin-bottom: 10px;
  }
  .badge-secondary {
    background-color: #17a2b8;
  }

  /* 배경색 추가 */
  .login-col {
    background-color: #f0f0f0; /* 밝은 회색 */
  }
  .calendar-col {
    background-color: #e9ecef; /* 조금 더 진한 회색 */
  }
  .reflection-col {
    background-color: #dee2e6; /* 더 진한 회색 */
  }

  /* 차트 표시 스타일 */
  #chart-area {
    display: none;
    margin-top: 20px;
  }

  </style>
</head>
<body>

<div class="container-fluid mt-5 custom-container">
  <h2>오늘의 QT</h2>
  <div class="card">
    <div class="card-header">QT(스포츠)</div>
    <div class="card-body">
      <!-- 그리드 시스템을 시작하는 행 -->
      <div class="row">
        <!-- 첫 번째 열, 12개 중 2개 열 차지 -->
      <div class="col-12 col-md-2 mb-3 login-col d-flex flex-column">
        <!-- 로그인 폼 -->
        <c:if test="${empty cus}">
        <form action="${cpath}/login" method="post">
          <div class="form-group">
            <label for="userId">아이디:</label>
            <input type="text" class="form-control" id="customer_id" placeholder="아이디 입력" name="customer_id">
          </div>
          <div class="form-group">
            <label for="password">패스워드:</label>
            <input type="password" class="form-control" id="password" placeholder="패스워드 입력" name="password">
          </div>
          <button type="submit" class="form-control btn btn-primary">로그인</button>
        </form>

        </c:if>
        <c:if test="${!empty cus}">
                <form action="${cpath}/logout" method="post">
                  <div class="form-group">
                    <label>${cus.customer_name}님 Welcome!!</label>
                  </div>
                  <button type="submit" class="form-control btn btn-primary">로그아웃</button>
                </form>
        </c:if>
      </div>

   <!-- 두 번째 열, 12개 중 7개 열 차지 -->
    <div class="col-12 col-md-7 mb-3 calendar-col d-flex flex-column">
       <!-- 달력 입력 필드 -->
      <label for="calendarInput">날짜 (YYYY-MM-DD):</label>
      <input type="date" id="calendarInput" class="form-control">
       <!-- 선택된 날짜를 표시하는 Card -->
    <div class="card mt-3" id="dateDisplayCard" style="display: none;">
    <div class="card-body">
        <h5 class="card-title" id="selectedDate">선택된 날짜:</h5>
        <h6 id="title">제목: </h6>
        <p id="text">본문: </p>
        <div id="detailList"></div> //여기에 동적으로 채워줌
    </div>
  </div>
  <!-- 차트를 표시할 영역 -->
  <div id="chart-area"></div>
  <!-- 차트 표시 버튼 -->
 <c:if test="${!empty cus}">
     <button type="button" class="btn btn-info mt-3" onclick="showChart()">Show Chart</button>
 </c:if>
 </div>

<script>
// 페이지 로드 시 오늘 날짜로 초기화
document.addEventListener('DOMContentLoaded', function() { //컨텐츠가 브라우저에 로딩이 되면 즉시 이벤트 처리

   var today = new Date();    // 날짜를 가져옴
    var year = today.getFullYear(); // 2024
    var month = (today.getMonth() + 1).toString().padStart(2, '0'); // 07   자바스크립트는 0부터시작해서 +1 한다음 두자리가 안되면 첫번째 숫자를 0으로
    var day = today.getDate().toString().padStart(2, '0'); // 18 =>day도 두자리수로 만드는역할
    var formattedDate = year + '-' + month + '-' + day; // 2024-07-18  3개를 합침

    document.getElementById('calendarInput').value = formattedDate; //여기에(인풋타입아이디에) 합친날짜를 넣어준다
    goBible(formattedDate);//서버로 날리기위에 goBible함수로 날린다 정보를

});

document.getElementById('calendarInput').addEventListener('change', function() { //달력이 체인지되면 서버로 날림
   var selectedDate = this.value;
   goBible(selectedDate); //날짜를 선택해도 goBible이 호출된다
});

function goBible(selectedDate){ //페이지가 로드될때나 달력에서 선택할때 발동
    document.getElementById('dateDisplayCard').style.display = 'block'; //change가 발생하면 보이게 처리해주는것이다.  제목이랑 본문을 뿌려주기 위해서
    document.getElementById('selectedDate').textContent = "선택된 날짜: " + selectedDate; //매개변수로 받은 == 가져올 날짜를 뿌려준다
    console.log(JSON.stringify({date: selectedDate}));
      fetch('${cpath}/proxy/date', { //서버에 요청
            method: 'POST',//포스트방식은 body부분에 json형식으로 가야한다 get방식은 fetch함수안에 ?키워드로 보낼수 있지만
            headers: {
                'Content-Type': 'application/json',                 //json타입으로
            },
            body: JSON.stringify({date: selectedDate})              //date라는 이름으로 매개변수 값이 날라간다 ->키 벨류
        })
        .then(response => response.text())                          //텍스트형태로 응답해줘서 텍스트형태로 가리킴 전테 html
        .then(html => {
            const parser = new DOMParser();                         //원하는 부분만 파싱하려고 DOMParser를 만들어서 사용함
            const doc = parser.parseFromString(html, 'text/html');  //-> parseFromString()은 문자열을 DOM 형식으로 변환해 줘.| 첫 번째 인자 → html → HTML 문자열 |   두 번째 인자 → 'text/html' → HTML로 파싱하겠다는 의미
                                                                    //한마디로 html부분에는 문자열로된 html이 들어있고 그문자열로된 html을 text/html이-> HTML로 파싱하겠다는 의미이다
                                                                    //그 파싱된 데이터를 이제 접근하게끔 만드는 작업이다 한마디로 -> HTML 문자열을 HTML DOM 객체로 변환

            const titleElement = document.getElementById('title'); //제목
            const textElement = document.getElementById('text'); // 본문
            const detailList = document.getElementById('detailList'); //리스트 를 뿌려주기 위한 공간을 const변수로 할당

            // 제목과 본문을 채움
            const bibleTextElement = doc.getElementById('bible_text'); //크롤링한 DOM트리중 아이디값이 bible_text라는 값을 찾음
            titleElement.textContent = bibleTextElement ? "제목: " + bibleTextElement.textContent.trim() : "제목: 정보를 찾을 수 없습니다.";  //3항연산자

            const bibleInfoElement = doc.getElementById('bibleinfo_box'); //아이디값이 bibleinfo_box 를 꺼내와
            textElement.textContent = bibleInfoElement ? bibleInfoElement.textContent.trim() : "본문: 정보를 찾을 수 없습니다."; //있으면 출력 없으면 else문자열 실행

            // 세부 목록을 채움
          detailList.innerHTML = ''; // 기존 내용 초기화
          const bodyListElement = doc.getElementById('body_list'); //크롤링한 아이디값이 body_list 인 부분을 찾아서 bodyListElement값에 할당
          if (bodyListElement) {   //bodyListElement값이 있으면
              const listItems = bodyListElement.querySelectorAll('li'); //크롤링한 태그 li 선택 해 listItems에 할당해 반복문 처리하게끔 만듬
              listItems.forEach((item, index) => {
                  const num = item.querySelector('.num').textContent.trim(); //반복문으로 크롤링한 class가 num 인 것을 가져와 변수에 할당
                  const info = item.querySelector('.info').textContent.trim();//반복문으로 크롤링한 class가 info 인 것을 가져와 변수에 할당

                  const detailItem = document.createElement('div'); //div를 하나 생성
                  // 마지막 항목에는 점선을 추가하지 않음
                  if (index < listItems.length - 1) { //index값이 아이템에 전체 길이값보다 작으면 //끝이 아니면 라인적용
                      detailItem.className = 'dotted-line'; // CSS 클래스 적용
                  }
                   // 복사 버튼 추가
                   detailItem.innerHTML = "<span class='badge badge-secondary'>" + num + "</span> " + info +
                   "<button class='btn btn-sm btn-secondary copy-btn' data-info='" + info + "'>C</button>";
                  detailList.appendChild(detailItem); //detailList 변수안에는 지금 id값을 가리키기때문에 div태그 아이디가 detailList 인곳에 동적으로 내용을 채워줌
              });

              // 모든 복사 버튼에 클릭 이벤트 추가
              const copyButtons = detailList.querySelectorAll('.copy-btn'); //새롭게 생긷 div 태그 에 copy-btn 을 가져와  copyButtons 에 집어넣음
              copyButtons.forEach(button => {   //copyButtons 안에 있는 '.copy-btn' 을 하나씩꺼내서 button으로 받음
                  button.addEventListener('click', function() { //해당하는 버튼이 클릭되면 콜백함수 실행
                      const info = this.getAttribute('data-info'); //(현재누른) 클릭된속성 값에서 'data-info'안에있는 info 내용을 info로 집어넣음
                      goCopy(info); //goCopy 함수호출
                  });
              });
          }
        })
        .catch(error => console.error('Error:', error));
}

function goCopy(info){
   document.getElementById("kakaoMessage").value = info;  //document.getElementById("kakaoMessage")는 id가 kakaoMessage인 HTML 요소를 선택하고, 그 요소의 .value에 info 값을 대입하는 거야.
}

// 카카오톡 메시지 전송         //공유하기 쇼핑몰 같은거에 활용가능
function sendKakaoMessage() {   //사용자가 버튼 클릭 시 실행됨
    var message = document.getElementById("kakaoMessage").value;    // message 변수에 사용자가 입력한 메시지를 저장

    if (message === '') { //만약 메시지가 비어있으면 경고창 출력 후 종료
        alert('카카오톡으로 전송할 메시지를 입력하세요.');
        return;
    }
                                        //Kakao.Link.sendDefault()를 사용해 카카오톡 메시지 전송
    Kakao.Link.sendDefault({ //카카오 링크로 보내는 방법(API를 사용해야함) 메세지로 보내려면 링크메서드가아닌 다른메서드를 사용해야함
        objectType: 'text',    //objectType: 'text' → 메시지 타입을 텍스트로 설정
        text: message,          //text → 전송할 텍스트 내용
        link: {                 //link → 메시지에 포함할 링크 (필요 시 추가 가능)

        }
    });
}
// 카카오톡 SDK 초기화
Kakao.init('cbf08383be04ce913b2361a26606b1f7'); // 여기에 발급받은 JavaScript 키를 입력하세요.



// 차트를 표시하는 함수
function showChart() {
    document.getElementById('chart-area').innerHTML="";
    // 차트 데이터를 서버에서 가져오기
    const customerId = '${cus.customer_id}'; // 실제 고객 ID로 교체
    fetch('${cpath}/proxy/monthly-data?customer_id='+customerId)
        .then(response => response.json())
        .then(data => {
            console.log(data); // ?
            // 데이터 가공   -> map() 함수는 내부적으로 for루프같은게 있어서 다 뽑아옴 정보를
            const categories = data.map(item => item.month); //배열안에 객체값을 지정하는역할 [{key:velue}]예시->[{month:2020-07-15},{count:20}]
            const seriesData = data.map(item => item.count);
            console.log(categories);
            console.log(seriesData);
            // 차트 영역을 보이도록 설정
            document.getElementById('chart-area').style.display = 'block';

            // Toast UI Chart 생성
            const chart = toastui.Chart.barChart({
                el: document.getElementById('chart-area'),
                data: {
                    categories: categories,
                    series: [
                        {
                            name: 'Count',
                            data: seriesData
                        }
                    ]
                },
                options: {
                    chart: {
                        title: 'Monthly Data Count in Bible Table',
                        width: 900,
                        height: 400
                    },
                    xAxis: {
                        title: 'Count'
                    },
                    yAxis: {
                        title: 'Month'
                    }
                }
            });
        })
        .catch(error => console.error('Error:', error));
}
</script>

<!-- 세 번째 열, 12개 중 3개 열 차지 -->
<div class="col-12 col-md-3 reflection-col d-flex flex-column">
  <form id="reflectionForm">
    <div class="form-group">
      <label for="godReflection">경기예상:</label> <!--나의 하나님-->
      <input type="text" class="form-control" id="bible_word" name="bible_word">
    </div>
    <div class="form-group">
      <label for="repentance">win:</label> <!--회개-->
      <textarea class="form-control" id="bible_repent" name="bible_repent" rows="2"></textarea>
    </div>
    <div class="form-group">
      <label for="insight">rosser:</label> <!--깨달음-->
      <textarea class="form-control" id="bible_things" name="bible_things" rows="2"></textarea>
    </div>
    <div class="form-group">
      <label for="action">느낀점:</label> <!--실천-->
      <textarea class="form-control" id="bible_implement" name="bible_implement" rows="2"></textarea>
    </div>
    <c:if test="${!empty cus}">
      <button type="button" class="btn btn-primary" onclick="goBibleInsert()">등록</button>
    </c:if>
     <c:if test="${empty cus}">
          <button type="button" class="btn btn-primary" onclick="goBibleInsert()" disabled>등록</button>
     </c:if>
    <button type="button" class="btn btn-secondary" onclick="resetForm()">취소</button>
  </form>

  <div class="mt-3">
      <label for="kakaoMessage">카카오톡 메시지:</label>
      <textarea class="form-control mb-2" id="kakaoMessage" rows="7" placeholder="카카오톡으로 전송할 메시지를 입력하세요."></textarea>
      <button type="button" class="btn btn-warning" onclick="sendKakaoMessage()">카카오톡 전송</button>
    </div>

</div>

<script>
// 폼 데이터 초기화 함수
function resetForm() {
    document.getElementById("reflectionForm").reset();
}

function goBibleInsert(){

    const customer_id="${cus.customer_id}";  // 로그인정보
    const bibleWord = document.getElementById('bible_word').value;
    const bibleRepent = document.getElementById('bible_repent').value;
    const bibleThings = document.getElementById('bible_things').value;
    const bibleImplement = document.getElementById('bible_implement').value;

    // {   } : Object -> JSON(String)
    //객체 오브젝트
    const data = { //json 데이터로 가공후 -> 전달하기위한 작업
        customer_id:customer_id,
        bible_word: bibleWord,
        bible_repent: bibleRepent,
        bible_things: bibleThings,
        bible_implement: bibleImplement
    };

    fetch('${cpath}/proxy/insertBible', { //데이터 입력저장하는 컨트롤러 요청
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data) //json으로 보낼때는 스트링으로 보내야 한다
    })
    .then(response => response.json()) //response.json()전에는 문자열이다 -> json오브젝트로 다시 바꿔줘야 한다
    .then(result => {
        if (result.success) {
            alert('데이터가 성공적으로 전송되었습니다.');
            resetForm(); // 전송 후 폼 초기화
        } else {
            alert('데이터 전송에 실패했습니다.');
        }
    })
    .catch(error => console.error('Error:', error));
}
</script>

      </div>
  </div>

    <div class="card-footer text-center">Java Spring Full Stack Developer(김남국)</div>
  </div>
</div>

</body>
</html>