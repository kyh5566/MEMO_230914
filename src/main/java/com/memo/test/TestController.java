package com.memo.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	@GetMapping("/test1")
	@ResponseBody
	public String test() {
		return "hello world!";
	}
	@GetMapping("/test2")
	@ResponseBody
	public Map<String, Object> test2() {
		Map<String,Object> result = new HashMap<>();
		result.put("1", 1);
		result.put("2", 2);
		result.put("3", 3);
		return result;
	}
	@GetMapping("/test3")
	public String test3() {
		return "test/test";
	}
}
