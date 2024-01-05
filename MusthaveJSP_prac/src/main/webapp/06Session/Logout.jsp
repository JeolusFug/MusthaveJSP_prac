<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//ほうほう１：かいいんにんしょうじょうほうぞくせいさくじょ
// 방법 1 : 회원인증정보 속성 삭제
session.removeAttribute("UserId");
session.removeAttribute("UserName");

//ほうほう２：すべてのぞくせいさくじょ
// 방법 2 : 모든 속성 한꺼번에 삭제
session.invalidate();

//ぞくせいさくじょごページのいどう
// 속성 삭제 후 페이지 이동
response.sendRedirect("LoginForm.jsp");
%>