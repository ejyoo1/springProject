package com.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PostIntercepter extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		String turn = (String)modelAndView.getModel().get("turn"); //model은 map으로 되어 있어서 (String)캐스팅을 해준다.
		
		if(!(turn == null || turn .isEmpty())){
			modelAndView.setViewName(turn);
		}
	}

}
