package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // 스프링 빈으로 등록
public class FileManagerService {
	
	// 실제 업로드 된 이미지가 저장 될 경로(서버)
	public static final String FILE_UPLOAD_PATH="D:\\kimyonghee\\6_spring_project\\MEMO\\memo_workspace\\images/";
//집 public static final String FILE_UPLOAD_PATH="D:\\kimyonghee\\6_spring_project\\MEMO\\memo_workspace\\images/";
	
	//input:File 원본,userLoginId(폴더명)  output: 이미지경로
	public String savaFile(String loginId, MultipartFile file) {
		//폴더 생성
		// 예 : aaaa_84186765463/sun.png
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName; //D:\\kimyonghee\\6_spring_project\\MEMO\\memo_workspace\\images/aaaa_84186765463/sun.png"
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			// 폴더 생성 실패 시 이미지 경로 null 리턴
			return null;
		}
		
		// 실제 파일 업로드 시키기: byte 단위
		try {
			byte[] bytes = file.getBytes();
			// ★★★ 한글 이미지는 올릴수없으므로 영문자로 변환해서 올린다 ★★★
			Path path = Paths.get(filePath + "/" + file.getOriginalFilename());
			Files.write(path, bytes); // 실제 파일 업로드
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 실패시 BO에게 null 리턴
		}
		
		// 파일 업로드가 성공했으면 웹 이미지 url path 를 리턴
		// 주소는 ~로 될것이다.
		// images/aaaa_84186765463/sun.jpg
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
}
