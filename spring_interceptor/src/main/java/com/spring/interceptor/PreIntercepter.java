package com.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PreIntercepter extends HandlerInterceptorAdapter {
	
	// Return false 할 시 Handler 가 실행되지않음.
		// handler가 object 이므로 handler는 사용하지 않음. request와 response만 제어하고,
		// 리턴 타입이 boolean이기 때문에 컨트롤러의 실행을 제어하겠다 라는 의미.
	@Override //controller에게 가기전에 request, response를 먼저 받는다 ==> 가공할 수 있다. 뭔가 부적합할 때 return false를 쳐서 controller에게 가지 못하게 한다. 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = true;
		String state = request.getParameter("state");
		
		if(state == null || !state.equals("go")) {
			response.sendRedirect(request.getContextPath());
			result = false; //controller 실행 안됨 -->pre_test가 실행되지 않고 ContextPath로  돌아간다. 
		}
		return result;
	}

	// 컨트롤러가 실행된 다음에 (Service - DAO 가 처리되고 Model에 담음)
		// 이 것의 목적은 Model에 뭐를 담았는지 확인하기 위한 목적
		// 컨트롤러가 결정해서 준 Model을 개발자가 가공해서 화면으로 넘기는 기능.
		// 모델과 화면을 조정할 수 있다.
		// HandlerAdaptor는 Controller가 준 것 처럼 view에 넘겨준다.
//	@Override //controller가 다 실행 된 후 Model에 무언가 담을 때 그 무언가를 확인할 때 post를 사용한다. controller가 model에게 준 값을 우리는 ModelAndView를 받기 때문에 model에 담긴것과, 화면을 조작할 수 있게 된다. 마치 controller가 보낸것처럼 자연스럽게 플로우 가능
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		
//		super.postHandle(request, response, handler, modelAndView);
//	}
//// 잘 안씀. Exception Handling 목적이지만 다른곳에서 사용.
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		
//		super.afterCompletion(request, response, handler, ex);
//	}

}
