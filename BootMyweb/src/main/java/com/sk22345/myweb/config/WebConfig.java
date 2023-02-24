package com.sk22345.myweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sk22345.myweb.util.interceptor.MenuHandler;
import com.sk22345.myweb.util.interceptor.UserAuthHandler;

@Configuration //개별적인 스프링 빈 설정 파일
public class WebConfig implements WebMvcConfigurer {
	
	@Bean //프리핸들러
	public UserAuthHandler userAuthHandler() {
		return new UserAuthHandler();
	}
	@Bean //포스트핸들러
	public MenuHandler menuHandler() {
		return new MenuHandler();
	}

	@Override //인터셉터 추가
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(userAuthHandler())
//				.addPathPatterns("/main")
//				.addPathPatterns("/product/*")
//				.addPathPatterns("/user/*")
//				.excludePathPatterns("/user/login")
//				.excludePathPatterns("/user/join");
				
//				.addPathPatterns("/**")
//				.excludePathPatterns("/user/login")
//				.excludePathPatterns("/user/join")
//				.excludePathPatterns("/js/*")
//				.excludePathPatterns("/css/*")
//				.excludePathPatterns("/img/*")
//				.rest api 제외;
		
		registry.addInterceptor(menuHandler())
				.addPathPatterns("/main")
				.addPathPatterns("/product/*")
				.addPathPatterns("/user/*");
	}
}
