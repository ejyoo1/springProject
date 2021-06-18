package com.spring.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	
	//컨트롤러 실행 전에 수행
	@RequestMapping("pre_test")
	public void pre_test() {}
	
	// 컨트롤러 실행 후에 수행
	@RequestMapping("post_test")
	public void post_test(Model model) {
		model.addAttribute("turn","after_test");
	}
	
	// 컨트롤러 수행 완료 Exception까지 넘어간 후에 수행
	@RequestMapping("after_test")
	public void after_test() throws SQLException{
		throw new SQLException();
	}
	
	
}
