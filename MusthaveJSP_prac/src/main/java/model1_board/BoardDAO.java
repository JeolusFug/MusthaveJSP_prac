package model1_board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.JDBConnect;
import jakarta.servlet.ServletContext;

public class BoardDAO extends JDBConnect {
	public BoardDAO(ServletContext application) {
		super(application);
	}
	//　けんさくじょうけんにあうけいじぶつのこすうをかえします
	// 검색 조건에 맞는 게시물의 개수를 반환
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;		//けっかをひょうじするへんすう 결과(게시물 수)를 담을 변수
		
		//　けいじぶつのこすうをしゅとくするクエリぶん
		// 게시물 수를 얻어오는 쿼리문 작성
		String query = "SELECT COUNT(*) FROM board";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
				   + " LIKE '%" + map.get("searchWord") + "%'";
		}
		
		try {
			stmt = con.createStatement();		// クエリぶん　せいせい//쿼리문 생성
			rs = stmt.executeQuery(query);		// クエリぶんのじっこう쿼리문 실행
			rs.next();		//カーソルをさいしょのぎょうにいどう 커서를 첫번째 행으로 이동
			totalCount = rs.getInt(1);		// 첫번째 컬럼 값을 가져옴
		}
		catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	// けんさくのじょうけんにあうけいじぶつもくろくをかえします
	// 검색 조건에 맞는 게시물 목록을 반환
	public List<BoardDTO> selectList(Map<String, Object> map) {
		List<BoardDTO> bbs = new Vector<BoardDTO>();		// けっかをひょうじするへんすう　결과(게시물 목록)를 담을 변수
		
		String query = "SELECT * FROM board ";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
				   + " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY num DESC ";		// DESC : 내림차순
		
		try {
			stmt = con.createStatement();		// クエリぶんのせいせい쿼리문 생성
			rs = stmt.executeQuery(query);		// クエリぶんのじっこう쿼리문 실행
			
			while (rs.next()) {		// つぎのないようがあるばあい(rs에)다음 내용이 있을 경우
				//けいじぶつひとつのないようをDTOにほぞん
				// 게시물 하나의 내용을 DTO에 저장
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));				//いちれんばんごう 일련 번호
				dto.setTitle(rs.getString("title"));			//タイトル 제목
				dto.setContent(rs.getString("content"));		//ないよう 내용
				dto.setPostdate(rs.getDate("postdate"));		//さくせいび 작성일
				dto.setId(rs.getString("id"));					//さくせえしゃⅠD 작성자 아이디
				dto.setVisitcount(rs.getString("visitcount"));	//さいせいすう 조회수
				
				bbs.add(dto);		//けっかもくろくにほぞん 결과 목록에 저장
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		return bbs;
	}

	// けんさくのじょうけんにあうけいじぶつもくろくをかえします（ページングきのう）
	// 검색 조건에 맞는 게시물 목록을 반환(페이징 기능 지원)
	public List<BoardDTO> selectListPage(Map<String, Object> map) {
		List<BoardDTO> bbs = new Vector<BoardDTO>();		//けっかをひょうじするへんすう// 결과(게시물 목록)를 담을 변수
		
		//クエリぶん テンプレート　쿼리문 템플릿
		String query = " SELECT * FROM ( "
					 + "	SELECT Tb.*, ROWNUM rNum FROM ( "
					 + "		SELECT * FROM board ";
		
		//けんさくじょうけんきのうついか 검색 조건추가
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
				   + " LIKE '%" + map.get("searchWord") + "%' ";
		}
		
		query += "		ORDER BY num DESC "
			   + "	) Tb "
			   + " ) "
			   + " WHERE rNum BETWEEN ? AND ?";
		
		try {
			// クエリぶんかんせい쿼리문 완성
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			
			// クエリぶんのじっこう
			// 쿼리문 실행
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				//けいじぶつひとつのないようをDTOにほぞん
				// 한 행(게시물 하나)의 데이터를 DTO에 저장
				BoardDTO dto = new BoardDTO();
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				//　かえすけっかもくろくにけいじぶつついか
				// 반환할 결과 목록에 게시물 추가
				bbs.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		//　もくろくのかえし
		// 목록 반환
		return bbs;
	}
	
	//デイタをDBについか
	// 게시글 데이터를 받아 DB에 추가
	public int insertWrite(BoardDTO dto) {
		int result = 0;
		
		try {
			//INSERTクエリぶんさくせい
			// INSERT 쿼리문 작성
            String query = "INSERT INTO board ( "
            			 + " num,title,content,id,visitcount) "
            			 + " VALUES ( "
            			 + " seq_board_num.NEXTVAL, ?, ?, ?, 0)";  
            
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;
	}
	
	//していしたけいじぶつのないようをへんきゃく
	// 지정한 게시물을 찾아 내용을 반환
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO();
		
		//クエリぶんのようい
		// 쿼리문 준비
		String query = "SELECT B.*, M.name "
					 + " FROM member M INNER JOIN board B "
					 + " ON M.id=B.id "
					 + " WHERE num=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);		//インパラメーターをいちれんばんごうにせってい 인파라미터를 일련번호로 설정
			rs = psmt.executeQuery();	//クエリぶんのじっごう 쿼리 실행
			
			//けっかのしょり
			// 결과 처리
			if (rs.next()) {
				dto.setNum(rs.getString(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString(6));
				dto.setName(rs.getString("name"));
			}
		}
		catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	//していしたけいじぶつのさいせいすうひとつあげる
	// 지정한 게시물의 조회수를 1 증가
	public void updateVisitCount(String num) {
		//クエリぶんのようい
		// 쿼리문 준비
		String query = "UPDATE board SET "
					 + " visitcount=visitcount+1 "
					 + " WHERE num=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);		//インパラメーターをいちれんばんごうにせってい 인파라미터를 일련번호로 설정
			psmt.executeQuery();		//クエリぶんのじっごう 쿼리 실행
		}
		catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}

	//していしたけいじぶつ
	// 지정한 게시물 수정
	public int updateEdit(BoardDTO dto) {
		int result = 0;
		
		try {
			
			//クエリぶんのようい
			// 쿼리문 준비
			String query = "UPDATE board SET "
						 + " title=?, content=? "
						 + " WHERE num=?";
			
			//クエリぶんのかんせい
			// 쿼리문 완성
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getNum());
			
			//クエリぶんのじっこう
			// 쿼리문 실행
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;		//けっかへんかん 결과 반환
	}
	
	//していしたけいじぶつをさくじょ
	// 지정한 게시물을 삭제
	public int deletePost(BoardDTO dto) {
		int result = 0;
		
		try {
			//クエリぶんのさくせい
			// 쿼리문 작성
			String query = "DELETE FROM board WHERE num=?";

			//クエリぶんのかんせい
			// 쿼리문 완성
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getNum());

			//クエリぶんのじっこう
			// 쿼리문 실행
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 삭제 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;		// けっかへんかん　결과 반환
	}
}