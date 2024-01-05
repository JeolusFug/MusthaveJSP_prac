<%@ page import="model1_board.BoardDTO" %>
<%@ page import="model1_board.BoardDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp"%>
<%
//フォームのあたいしゅとく
// 폼값 받기
String title = request.getParameter("title");
String content = request.getParameter("content");

//フォームのあたをDTOオブジェクトにほぞん
// 폼값을 DTO 객체에 저장
BoardDTO dto = new BoardDTO();
dto.setTitle(title);
dto.setContent(content);
dto.setId(session.getAttribute("UserId").toString());

//DAOオブジェクトをつじてDBにDTOをほぞん
// DAO 객체를 통해 DB에 DTO 저장
BoardDAO dao = new BoardDAO(application);
int iResult = dao.insertWrite(dto);
dao.close();

//せいこうとしっばい
// 성공 혹은 실패
if (iResult == 1) {
	response.sendRedirect("List.jsp");
} else {
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out);
}
%>