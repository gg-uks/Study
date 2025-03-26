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
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=카카오 자바스크립트키"></script> <!-- //지도라이브러리 -->
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
   <script src="https://unpkg.com/mqtt/dist/mqtt.min.js"></script> <!--//mqtt 서버에 있는 내용을 (수신자sub)이용할수 있는 API -->
  <style>
    .login-form {
      padding: 20px;
      background-color: #f8f9fa;
      border-radius: 5px;
    }
    .login-button, .register-button {
      width: 100%;
      padding: 8px;
    }
    .login-button {
      background-color: #007bff;
      color: white;
    }
    .register-button {
      background-color: #28a745;
      color: white;
      margin-top: 10px;
    }

     #map { width: 100%; height: 400px; } /* 지도 크기 설정 */

  </style>
<script>
        var map;
        function initMap() {
            // 지도 생성 및 객체 생성
            map = new kakao.maps.Map(document.getElementById('map'), {
                center: new kakao.maps.LatLng(37.5665, 126.9780), // 초기 중심 좌표 설정
                level: 3 // 지도의 확대 레벨
            });
        }

        window.onload = initMap; // 윈도우 로드 시 지도 초기화   윈도우에서 제공해주는 함수라 저렇게 넣어도 페이지가 로드된후 자동실행된다

        //MQTT 서버에 접속하려면 웹소켓 서버를 통한 포트매핑을 통하여 접속해야한다
        const client = mqtt.connect('ws://localhost:9001'); // MQTT 브로커 연결   ws=웹소켓   웹에서 접속할수있는 서버를 열어줘야 한다
        //내부적으로(ws) 9001 포트랑 (Mqtt)1883서버랑 연결되어있다
        client.on('connect', function () { //연결이 되면 어디에서 읽어올것인지 토픽을 등록하는 부분이다{client.subscribe이 메서드가}
            console.log('Connected to MQTT Broker on ws://localhost:9001');
            client.subscribe('user/+/topic'); // 수신서버등록   //가운데 + 부분이  동적으로 바뀔수있게 설정해주는것이다 + 는
        });
       //                                            user/user03/topic
        client.on('message', function (topic, message) {  //메시지가 들어오면(수신자 sub를 뜻한다) 함수가 실행된다 topic,이랑 message를 전달한다  |  topic은 +부분에 들어온 유저를 뜻한다 |message는 메시지정보를 뜻한다 수신자 에 있는 곳
            console.log("Message from"+topic +":"+message.toString()); //유저 : 위도 경도  콘솔로 확인
            document.getElementById('messages').innerHTML += "<p>"+message.toString()+"</p>"; //수신자에 있는 내용을 넣어준다 ID태그 messages에다가

            const data = JSON.parse(message); // 메시지 를 JSON으로   파싱
            const latLng = new kakao.maps.LatLng(data.lat, data.lng); //  -> \기준으로 끊어논 lat ,lng 의 값을 키와 벨류로  키값을 가져와서 변수에 집어넣은데 객체(JSON) 형태로

            // 마커 생성 및 지도에 추가
            new kakao.maps.Marker({
                position: latLng,
                map: map
            });

            // 지도의 중심을 마커 위치로 이동
            map.setCenter(latLng);

            const topicParts = topic.split('/'); // "user/user01/topic"     //  /를 기준으로 자름
            const userId = topicParts[1]; // 'user/{user_id}/topic' 형식이라고 가정  가운데부분을 가지고옴 유저아이디 누가 보냈는지  1번은 어떤사용자가 보냈는지 구분하기위한 작업

            fetch(`${cpath}/api/users/` + userId)  //누가 요청했는지 처리하기위한 작업 서버단으로 넘겨서
                .then(response => {
                    if (!response.ok) throw new Error('User not found');
                    return response.json();
                })
                .then(userData => {
                    console.log(userData);
                    document.getElementById('userDetails').innerHTML = "User Name: " + userData.customer_name;
                })
                .catch(error => {
                    console.error('Error fetching user data:', error);
                    document.getElementById('userDetails').innerHTML = "사용자가 없습니다.";
                });

        });

    </script>
</head>
<body>

<div class="container-fluid mt-3">
  <h1>Java Spring Full Stack Developer</h1>
  <p>Resize the browser window to see the effect.</p>
  <div class="container-fluid">
    <div class="card">
      <div class="card-header">Java Spring Framework</div>
      <div class="card-body">
        <div class="row">
          <div class="col-sm-2 mb-2">
            <div class="card login-form">
              <div class="card-body">
                <h4 class="card-title">회원 로그인</h4>
                <form>
                  <div class="form-group">
                    <label for="username">사용자 이름:</label>
                    <input type="text" class="form-control" id="username" placeholder="Enter username">
                  </div>
                  <div class="form-group">
                    <label for="password">비밀번호:</label>
                    <input type="password" class="form-control" id="password" placeholder="Enter password">
                  </div>
                  <button type="submit" class="btn login-button">로그인</button>
                  <a href="${cpath}/register" class="btn register-button">회원가입</a>
                </form>
              </div>
            </div>
          </div>
          <div class="col-sm-7 mb-2">
            <div class="card">
              <div class="card-body">
                <h4 class="card-title">위치 추적 시스템</h4>
                <p class="card-text">위도 경도가 변경시 지도가 실시간으로 변경된다</p>
                <h1>MQTT Message Receiver</h1>
                <div id="messages"></div>
                 <div id="map"></div> <!-- 지도를 표시할 div 요소 -->
              </div>
            </div>
          </div>
          <div class="col-sm-3">
            <div class="card">
              <div class="card-body">
                <h4 class="card-title">회원정보</h4>
                <p class="card-text">여기에 회원의 정보가 출력이 된다.</p>
                <div id="userDetails"></div>
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