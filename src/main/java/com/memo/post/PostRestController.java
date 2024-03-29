package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
	@Autowired
	private PostBO postBO;
	/**
	 *  글쓰기 완료
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/create") // ajax의 request url 주소
	public Map<String, Object> create(
			@RequestParam("subject")String subject
			,@RequestParam("content")String content
			,@RequestParam(value = "file", required = false) MultipartFile file
			,HttpSession session) {
			
		//글쓴이 번호 세션에 있는 userId 를 꺼낸다.
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// db insert
		 postBO.addPost(subject,content,userId,file,userLoginId);
		
		//응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	/**
	 * 글수정 API
	 * @param postId
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PutMapping("/update")
	public Map<String, Object> update(
			@RequestParam("postId") int postId
			,@RequestParam("subject") String subject
			,@RequestParam("content") String content
			,@RequestParam(value = "file", required = false) MultipartFile file
			,HttpSession session) {
		
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		// db 업데이트
		postBO.updatePostByPostId(userId,userLoginId,postId, subject, content, file);
		
		// ajax 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	/**
	 * 글 삭제 API
	 * @param deleteId
	 * @return
	 */
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("deleteId") int deleteId
			,HttpSession session) {
		
		
		int userId =(Integer) session.getAttribute("userId");
		
		
		postBO.deletePost(deleteId,userId);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
