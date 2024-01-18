package com.memo.post;

import java.util.List;

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
	public String postListView(Model model,HttpSession session) {
		// 로그인 여부 조회
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			// 비로그인 이면 로그인 화면으로 이동
			return "redirect:/user/sign-in-view";
		}
		
		// 글목록 db 조회
		List<Post> PostList = postBO.getPostListByUserId(userId);
		
		model.addAttribute("viewName","post/postList");
		model.addAttribute("postList",PostList);
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
