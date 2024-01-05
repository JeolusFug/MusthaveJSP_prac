package utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieManager {
	//めいじしたなまえ、あたい、いじきかんをじょうけんとしてあたらしいcookieをせいせいします
    // 명시한 이름, 값, 유지 기간 조건으로 새로운 쿠키를 생성합니다.
    public static void makeCookie(HttpServletResponse response, String cName,
            String cValue, int cTime) {
        Cookie cookie = new Cookie(cName, cValue); //cookieせいせい 쿠키 생성
        cookie.setPath("/");         // けいろせってい　경로 설정
        cookie.setMaxAge(cTime);     // いじきかんせってい유지 기간 설정
        response.addCookie(cookie);  // へんじオブジェクトについか응답 객체에 추가
    }

    //めいじしたなまえのcookieにあたいをへんかん
    // 명시한 이름의 쿠키를 찾아 그 값을 반환합니다.
    public static String readCookie(HttpServletRequest request, String cName) {
        String cookieValue = "";  //へんかんあたい 반환 값

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                String cookieName = c.getName();
                if (cookieName.equals(cName)) {
                    cookieValue = c.getValue();  //へんかんあたいこうしん 반환 값 갱신
                }
            }
        }
        return cookieValue;
    }

    //めいじしたなまえのcookieをさくじょ
    // 명시한 이름의 쿠키를 삭제합니다.
    public static void deleteCookie(HttpServletResponse response, String cName) {
        makeCookie(response, cName, "", 0);
    }
}