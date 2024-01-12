package com.memo.post.mapper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/post")
@Controller
public class PostController {
	@PostMapping("/post-list-view")
	public String postListView(Model model) {
		model.addAttribute("viewName","post/postList");
		return "template/layout";
	}
}
