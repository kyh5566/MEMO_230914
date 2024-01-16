package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.post.mapper.PostMapper;
import com.memo.post.postlist.Post;

@Service
public class PostBO {
	@Autowired
	private PostMapper postMapper;
	
	//input : 로그인된 사람의 userId    output: List<Post>
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
}
