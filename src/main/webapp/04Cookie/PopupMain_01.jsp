<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String popupMode = "on";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠키를 이용한 팝업 관리 ver 0.1</title>
<style>		<!--여기는 CSS, 팝업창의 위치, 생상등의 형태를 정함. absolute는 웹 브라우저상에서 절대 위치를 설정하게 해주는 속성값 -->
	div#popup {
		position: absolute; top:100px; left:50px; color:yellow;
		width:270px; height:100px; background-color:gray;
	}
	div#popup>div {
		position: relative; background-color:#ffffff; top:0px;
		border:1px solid gray; padding:10px; color:black;
	}
</style>

<!--밑은 jQuery를 이용하는 자바 스크립트 코드 -->
<script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
$(function() {
	$('#closeBtn').click(function(){	//닫기 버튼(id="closeBtn")을 누르면
		$('#popup').hide();				//팝업창(id="popup")을 숨김 처리한다.
	});
});
</script>
</head>
<body>
<h2>팝업 메인 페이지(ver0.1)</h2>
<%
	for (int i = 1; i <= 10; i++) {
		out.print("현재 팝업창은" + popupMode + " 상태입니다.<br/>");
	}

	if (popupMode.equals("on")) {	//popupMode 값이 "on"일 때만 팝업창 표시
%>		
	<div id = "popup">		<!-- 공지사항 팝업 화면 -->
		<h2 align = "center">공지사항 팝업입니다.</h2>
		<div align = "right"><form name = "popFrm">
			<input type = "checkbox" id = "inactiveToday" value = "1" />
			하루 동안 열지 않음
			<input type = "button" value = "닫기" id = "closeBtn" />
		</form></div>
	</div>
<%
	}
%>
</body>
</html>