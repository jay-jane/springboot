package com.sk22345.myweb.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class UserAuthHandler implements HandlerInterceptor {
	
	/*
	 * 1. HandlerInterceptor 상속
	 * 
	 * preHandle - 컨트롤러 진입 전 실행
	 * postHandle - 컨트롤러 실행 후 실행
	 * afterCompletion - 화면으로 가기 직전 수행
	 * 
	 * 2. 인터셉터 클래스를 Bean으로 등록
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("인터셉터 실행");
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		if(user_id == null) { //로그인 안 됨
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		return true; //true - 컨트롤러 실행
	}
}
