<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="model1_board.BoardDTO"%>
<%@ page import="model1_board.BoardDAO"%>
<%@ page import="utils.BoardPage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//DAOをせいせいしDBにせつぞく
// DAO를 생성해 DB에 연결
BoardDAO dao = new BoardDAO(application);

//ユーザーがにゅうりょくしたけんさくじょうけんをMAPにほぞん
// 사용자가 입력한 검색 조건을 MAP에 저장
Map<String, Object> param = new HashMap<String, Object>();
String searchField = request.getParameter("searchField");
String searchWord = request.getParameter("searchWord");
if (searchWord != null) {
	param.put("searchField", searchField);
	param.put("searchWord", searchWord);
}

int totalCount = dao.selectCount(param);		//けいじぶつのかずかくにん 게시물 수 확인

//ページングしょりのはじまり 페이지 처리 시작
//ぜんページすうをけいさん전체 페이지 수 계산
int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
int totalPage = (int)Math.ceil((double)totalCount / pageSize);		// 전체 페이지수

//げんざいのページかくにん
// 현재 페이지 확인
int pageNum = 1;		// デフォルト기본값
String pageTemp = request.getParameter("pageNum");
if (pageTemp != null && !pageTemp.equals(""))
	//パラメータにページばんごうがそうしんされたら、そのばんごうをしようします
	pageNum = Integer.parseInt(pageTemp);		// 요청 받은 페이지로 수정
	
//もくろくにしゅつりょくするけいじぶつのはんいをけいさんします
// 목록에 출력할 게시물 범위 계산
int start = (pageNum - 1) * pageSize + 1;		// はつけいじぶつのばんご첫 게시물 번호
int end = pageNum * pageSize;		//さいごのけいじぶつのばんご 마지막 게시물 번호
param.put("start", start);
param.put("end", end);
//ページングしょりのおわり
// 페이지 처리 끝
	

List<BoardDTO> boardLists = dao.selectListPage(param);		//けいじぶつのもくろくしゅとく 게시물 목록 받기
dao.close();		//DBせつぞくしゅりょう DB 연결 닫기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원제 게시판</title>
</head>
<body>
	<jsp:include page="../Common/Link.jsp" />		<!-- きょうつうリンク공통 링크(전에 만든것) -->
	
	<h2>목록 보기(List) - 현재 페이지 : <%= pageNum %> (전체 : <%= totalPage %>)</h2>
	<!-- けんさくフォーム검색폼 -->
	<form method="get">
	<table border="1" width="90%">
	<tr>
		<td align="center">
			<select name="searchField">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchWord" />
			<input type="submit" value="검색하기" />
		</td>
	</tr>
	</table>
	</form>
	<!-- けいじぶつのもくろくテーブル게시물 목록 테이블(표) -->
	<table border="1" width="90%">
		<!-- それぞれカラムのなまえ각 컬럼의 이름 -->
		<tr>
			<th width="10%">번호</th>
			<th width="50%">제목</th>
			<th width="15%">작성자</th>
			<th width="10%">조회수</th>
			<th width="15%">작성일</th>
		</tr>
		<!--　もくろくのないよう 목록의 내용 -->
<%
if (boardLists.isEmpty()) {
	//けいじぶつがひとつもないばあい
	// 게시물이 하나도 없을때
%>		
		<tr>
			<td colspan="5" align="center">
				등록된 게시물이 없습니다.
			</td>
		</tr>
<%
}
else {
	//けいじぶつがあるばあい
	// 게시물이 있을 때
	int virtualNum = 0;	 //がめんじょうのけいじぶつのばんご 화면상에서의 게시물 번호
	int countNum = 0;
	for (BoardDTO dto : boardLists)
	{
		//ぜんけいじぶつからはじめひとつずつげんしょう	//きそんのコード、ページングごちゅうしゃくしょり
		//virtualNum = totalCount--;	// 전체 게시물 수에서 시작해 1씩 감소 // 기존 코드, 페이징 추가이후 주석처리
		virtualNum = totalCount - (((pageNum - 1) * pageSize) + countNum++);
%>		
		<tr align="center">
			<td><%= virtualNum %></td>	<!-- けいじぶつのばんご게시물 번호 -->
			<td align="left">		<!-- タイトル(+ハイパーリンク)제목(+하이퍼링크) -->
				<a href="View.jsp?num=<%= dto.getNum() %>"><%= dto.getTitle() %></a>
			</td>
			<td align="center"><%= dto.getId() %></td>		<!-- さくせいしゃID작성자 아이디 -->
			<td align="center"><%= dto.getVisitcount() %></td>	<!-- さいせいすう조회수 -->
			<td align="center"><%= dto.getPostdate() %></td>	<!-- さいせいび작성일 -->
		</tr>
<%		
	}
}
%>		
	</table>
	<!-- もくろくかぶの「かきこみ」ボタン목록 하단의 [글쓰기]버튼 -->
	<table border="1" width="90%">
		<tr align="center">
			<!-- ページングしょり페이징 처리 -->
			<td>
				<%= BoardPage.pagingStr(totalCount, pageSize,
	                       blockPage, pageNum, request.getRequestURI()) %>  
			</td>
			<!-- かきこみボタン글쓰기 버튼  -->
			<td><button type="button" onclick="location.href='Write.jsp';">글쓰기</button></td>
		</tr>
	</table>
</body>
</html>

