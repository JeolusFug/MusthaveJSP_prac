<%@ page import="model1_board.BoardDAO"%>
<%@ page import="model1_board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp"%>
<%
//しゅうせいのないようかくとく
// 수정 내용 얻기
String num = request.getParameter("num");
String title = request.getParameter("title");
String content = request.getParameter("content");

//　DTOにほぞん
// DTO에 저장
BoardDTO dto = new BoardDTO();
dto.setNum(num);
dto.setTitle(title);
dto.setContent(content);

//　DBにはんえい
// DB에 반영
BoardDAO dao = new BoardDAO(application);
int affected = dao.updateEdit(dto);
dao.close();
//せいこうおよびしっばいのしょり
// 성공 혹은 실패 처리
if (affected == 1) {
	//せこうしたらしょうさいひょうじページにいどう
	// 성공시 상세 보기 페이지로 이동
	response.sendRedirect("View.jsp?num=" + dto.getNum());
}
else {
	//しっばいのばあいまえのページにいどう
	// 실패시 이전 페이지로 이동
	JSFunction.alertBack("수정하기에 실패하였습니다.", out);
}
%>