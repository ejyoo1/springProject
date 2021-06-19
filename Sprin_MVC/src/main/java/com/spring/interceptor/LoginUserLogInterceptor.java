package com.spring.interceptor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.spring.dto.MemberVO;

public class LoginUserLogInterceptor extends HandlerInterceptorAdapter {
	// 로그인 컨트롤러가 종료될 때,
	// 세션에 로그인 유저가 등록되는 순간 로그 처리
	
	// 파라미터는 조정 불가 (컨트롤러가 아님. - 주어진 형태로 그대로 사용)
	// post로 된 이유는 loginController가 끝난뒤에 수행해야 하며
	// 세션에 대한 조회를 해야 함.
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		MemberVO loginUser=(MemberVO)request.getSession().getAttribute("loginUser");
		
		if(loginUser==null) return;
		
		// 로그인 정보를 스트림으로 저장.
		// ','를 해야 csv 형태로 만들어질 수 있음.
		String tag ="[login:user]";
		String log =tag+","
					+loginUser.getId()+","					
					+loginUser.getPhone()+","
					+loginUser.getEmail()+","
					+request.getRemoteAddr()+","
					+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		// FileWriter에 true를 주어야 이어쓰기가 됨.
		// 파일 처리
		String savePath = "d:\\log";
		File file = new File(savePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		String logFilePath=savePath + File.separator+"login_user_log.csv";
		BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath,true));
		
		// 로그를 기록
		out.write(log);
		out.newLine();
		
		out.close();
	}
}
