package membership;

import common.JDBConnect;

public class MemberDAO extends JDBConnect {
	//　めいじしたデータベースのせつぞくがかんりょうした　memberDAO	オブジェクト(?)をせいせいします
    // 명시한 데이터베이스로의 연결이 완료된 MemberDAO 객체를 생성합니다.
    public MemberDAO(String drv, String url, String id, String pw) {
        super(drv, url, id, pw);
    }

    //　めいじしたID/PWといっちするかいいんじょうほをかえします
    // 명시한 아이디/패스워드와 일치하는 회원 정보를 반환합니다.
    public MemberDTO getMemberDTO(String uid, String upass) {
        MemberDTO dto = new MemberDTO();  //かいいんじょうほDTOオブジェクトをせいせい// 회원 정보 DTO 객체 생성
        String query = "SELECT * FROM member WHERE id=? AND pass=?";  //クエリぶん　テンプレート// 쿼리문 템플릿

        try {
            // クエリのじっこう//　쿼리 실행
            psmt = con.prepareStatement(query); //　ダイナミッククエリぶんのようい// 동적 쿼리문 준비
            psmt.setString(1, uid);    //クエリ文のさいしょのインパラメーターにあたにをせってい// 쿼리문의 첫 번째 인파라미터에 값 설정
            psmt.setString(2, upass);  //クエリ文のふたつめのインパラメーターにあたにをせってい// 쿼리문의 두 번째 인파라미터에 값 설정
            rs = psmt.executeQuery();  // クエリぶんのじっこう// 쿼리문 실행

            // けっかをしょりします//결과 처리
            if (rs.next()) {
            	//クエリのけっかとしてえられたかいいんじょうほをDTOオブジェクトにほぞんします
                // 쿼리 결과로 얻은 회원 정보를 DTO 객체에 저장
                dto.setId(rs.getString("id"));
                dto.setPass(rs.getString("pass"));
                dto.setName(rs.getString(3));
                dto.setRegidate(rs.getString(4));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;  //DTOオブジェクトをかえします //DTO 객체 반환
    }
}
