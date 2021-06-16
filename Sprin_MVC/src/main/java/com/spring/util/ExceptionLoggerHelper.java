package com.spring.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.spring.dto.MemberVO;

// 예외 로그 찍기
public class ExceptionLoggerHelper {
	public static void write(HttpServletRequest request, Exception e, Object res) {
		String savePath = GetUploadPath.getUploadPath("error.log").replace("/", File.separator);
		String logFilePath = savePath + File.separator + "system_exception_log.csv";
		
		String url = request.getRequestURI().replace(request.getContextPath(),"");
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
		String loginUserName = ((MemberVO) request.getSession().getAttribute("loginUser")).getName();
		String exceptionType = e.getClass().getName();
		String happenObject = res.getClass().getName();
		
		String log = "[Error : " + exceptionType + "]" + url + "," + date + ","
				+ loginUserName + "," + happenObject + "\n" + e.getMessage();
		
		File file = new File(savePath);
		file.mkdir();
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath, true)); // true 를 주어 이어쓰기
			// 로그를 기록
			out.write(log);
			out.newLine();
			
			out.close();
		} catch (IOException exception){
			exception.printStackTrace();
		}
	}
}
