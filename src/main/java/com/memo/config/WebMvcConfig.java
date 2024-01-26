package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;
import com.memo.interceptor.PermissionInterceptor;

@Configuration // 설정을 위한 스프링빈 등록
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	private PermissionInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(interceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/static/**", "/error", "/user/sign-out")
		;
	}
	
	// 웹 이미지 path 와 서버에 업로드된 실제 이미지와 맵핑시키기
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry 
			registry) {
		registry.
		addResourceHandler("/images/**") 
		// web path : http://localhost/images/aaaa_1705483574977/img1.jpg
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH);// 실제 파일 위치
		//windows 는 /// 3개 필수
	}
}
