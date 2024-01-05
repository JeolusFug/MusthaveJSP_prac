<%@ page import="model1_board.BoardDTO" %>
<%@ page import="model1_board.BoardDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp"%>
<%
// 폼값 받기
String title = request.getParameter("title");
String content = request.getParameter("content");

// 폼값을 DTO 객체에 저장
BoardDTO dto = new BoardDTO();
dto.setTitle(title);
dto.setContent(content);
dto.setId(session.getAttribute("UserId").toString());

// DAO 객체를 통해 DB에 DTO 저장
BoardDAO dao = new BoardDAO(application);
int iResult = dao.insertWrite(dto);
// int iResult = 0;
// for (int i = 1; i <= 100; i++) {
// 	dto.setTitle(title + "-" + i);
// 	iResult = dao.insertWrite(dto);
// }		// 더미데이터 많이넣기위한 함수
dao.close();

// 성공 혹은 실패
if (iResult == 1) {
	response.sendRedirect("List.jsp");
} else {
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out);
}
%>