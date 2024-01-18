package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.mapper.PostMapper;
import com.memo.post.postlist.Post;

@Service
public class PostBO {
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private FileManagerService fileManagerService;
	
	//input : 로그인된 사람의 userId    output: List<Post>
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	//input : params    output: X
	public void addPost(String subject, String content,int userId,
			MultipartFile file,String userLoginId) {
		
		String imagePath = null;
		// 업로드할 이미지가 있을때만 업로드
		if(file != null) {
			imagePath = fileManagerService.savaFile(userLoginId, file);
		}
		
		postMapper.insertPost(subject,content,userId,imagePath,userLoginId);
	}
	
	public Post getPostByuserIdpostId(int userId, int postId) {
		return postMapper.selectPostByuserIdpostId(userId, postId);
	}
}
