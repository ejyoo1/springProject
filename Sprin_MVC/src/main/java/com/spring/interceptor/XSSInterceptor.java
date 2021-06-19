package com.spring.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.josephoconnell.html.HTMLInputFilter;

// 공지사항 sitemesh 사용 시 title의 js 문법 실행되는 문제 해결하기 위해 Xss 코딩
// Multipart일 때는 request.getParameter하는것이지 이것을 사용하면 안됨.
public class XSSInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { // 자료실 할 떄 한번 더 할 것임
		Enumeration<String> crossParamNames = request.getParameterNames();
		
		while(crossParamNames.hasMoreElements()) {

			String paramName = crossParamNames.nextElement();
			String paramValue = request.getParameter(paramName);

			request.setAttribute("XSS"+paramName, HTMLInputFilter.htmlSpecialChars(paramValue));				

		}
		return true;
	}
	//request -> resolver -> 파싱된 Request(파일처리 모두 된) -> interceptor 수행
	// 결과적으로 파일도 별다른 설정 없이 편하게 파라미터 처리 가ㅡㅇ.
	
}
