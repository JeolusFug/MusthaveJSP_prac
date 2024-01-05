<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table border="1" width"90%">
	<tr>
		<td align="center">
		<!-- ログインするかどうかによるメニューのへんか로그인 여부에 따른 메뉴 변화 -->
		<% if (session.getAttribute("UserId") == null) { %>
			<a href="../06Session/LoginForm.jsp">로그인</a>
		<% } else { %>
			<a href="../06Session/Logout.jsp">로그아웃</a>
		<% } %>
			<!-- かいいんせいけいじばんでしようするリンク -->
			<!-- 회원제 게시판에서 사용할 링크 -->
							　　<!--メニューかんのスペースかくほようのとくしゅもじ -->
			&nbsp;&nbsp;&nbsp; <!-- 메뉴 사이의 공백(space) 확보용 특수 문자 -->
			<a href="../08Board/List.jsp">게시판(페이징X)</a>
			&nbsp;&nbsp;&nbsp;
			<a href="../09PagingBoard/List.jsp">게시판(페이징O)</a>
		</td>
	</tr>
</table>