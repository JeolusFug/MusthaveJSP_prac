package utils;

import jakarta.servlet.jsp.JspWriter;

public class JSFunction {
	//メッセージつうちウィンドウをひらき、めいじしたURLにいどうします
	// 메시지 알림창을 띄운 후 명시한 URL로 이동
	public static void alertLocation(String msg, String url, JspWriter out) {
		try {
			String script = ""	//そうにゅうするジャバスクリプトコード삽입할 자바스크립트 코드
					+ "<script>"
					+ "		alert('" + msg + "');"
					+ "		location.href='" + url + "';"
					+ "</script>";		//ジャバスクリプトコードをoutないぞうオブジェクトにそうにゅうします
			out.println(script);		//자바 스크립트 코드를 out 내장 객체로 출력(삽입)
		}
		catch (Exception e) {}
	}

	//メッセージつうちウィンドウをひらき、まえのページにもどります
	//메시지 알림창을 띄운 후 이전 페이지로 돌아감
	public static void alertBack(String msg, JspWriter out) {
		try {
			String script = ""
					+ "<script>"
					+ "		alert('" + msg + "');"
					+ "		history.back();"
					+ "</script>";
			out.println(script);
		}
		catch (Exception e) {}
	}
}
