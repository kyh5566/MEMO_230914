package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;

@Configuration // 설정을 위한 스프링빈 등록
public class WebMvcConfig implements WebMvcConfigurer{
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
