package com.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@GetMapping("/sign-up-view")
	public String signUPView(Model model) {
		model.addAttribute("viewName","user/signUp");
		return "template/layout";
	}
	@GetMapping("/sign-in-view")
	public String signInView(Model model) {
		model.addAttribute("viewName","/user/signIn");
		return "template/layout";
	}
	
}