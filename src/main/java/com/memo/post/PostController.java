package com.memo.post;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.postlist.Post;

import jakarta.servlet.http.HttpSession;
@RequestMapping("/post")
@Controller
public class PostController {
	@Autowired
	private PostBO postBO;
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/post-list-view")
	public String postListView(
			@RequestParam(value = "prevId", required = false) Integer prevIdParam
			,@RequestParam(value = "nextId", required = false) Integer nextIdParam
			,Model model,HttpSession session) {
		// 로그인 여부 조회
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			// 비로그인 이면 로그인 화면으로 이동
			return "redirect:/user/sign-in-view";
		}
		
		// 글목록 db 조회
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		int nextId = 0;
		int prevId = 0;
		if (postList.isEmpty() == false) {
			// postList 가 비어있을때 오류 방지
			prevId = postList.get(0).getId(); // 가져온 리스트의 첫번째 글 번호
			nextId = postList.get(postList.size() - 1).getId(); // 가져온 리스트의 마지막 글 번호
			
			// 이전 방향의 끝
			// prevId 와 post 테이블의 가장 큰 id 값과 같으면 이전 페이지 없음
			if (postBO.isPrevLastPageByUserId(userId, prevId)) {
				prevId = 0;
			}
			
			// 다음 방향의 끝
			// nextId 와 post 테이블의 가장 작은 id값과 같으면 다음 페이지 없음
			if (postBO.isNextLastPageByUserId(userId, nextId)) {
				nextId = 0;
			}
		}
		
		model.addAttribute("nextId", nextId);
		model.addAttribute("prevId", prevId);
		model.addAttribute("viewName","post/postList");
		model.addAttribute("postList",postList);
		return "template/layout";
	}
	/**
	 * 글쓰기 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/post-create-view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName","post/postCreate");
		return "template/layout";
	}
	
	@GetMapping("/post-detail-view")
	public String postDetailView(Model model
			,@RequestParam("postId") int postId
			,HttpSession session) {
		
		int userId =  (int)session.getAttribute("userId");
		// db 조회 - postId , 세션에서 userId
		Post post = postBO.getPostByuserIdpostId(userId, postId);
		
		model.addAttribute("post", post);
		model.addAttribute("viewName","post/postDetail");
		return "template/layout";
	}
}
