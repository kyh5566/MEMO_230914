package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.mapper.PostMapper;
import com.memo.post.postlist.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBO {
	//private Logger logger = LoggerFactory.getLogger(PostBO.class);
	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	//input : params
	public void updatePostByPostId(int userId,
			String userLoginId,
			int postId, 
			String subject, 
			String content, 
			MultipartFile file) {
		
		// 기존 글을 가져온다.(1. 이미지 교체시 삭제하기위함 2. 업데이트 대상이 유효한지 확인)
		Post post = postMapper.selectPostByuserIdpostId(userId, postId);
		if (post == null) {
			log.info("[글 수정] post is null. postId:{}, userId{}", postId, userId);
			return;
		}
		
		// 이미지파일 있을경우에만.
		// 1) 새 이미지 업로드
		// 2) 1번단계가 성공하면 기존 이미지 제거(기존이미지가 있을시에만.)
		String imagePath = null;
		if (file != null) {
			// 업로드
			imagePath = fileManagerService.savaFile(userLoginId, file);
			// 업로드 성공 시 기존이미지가 있으면 제거
			if (imagePath != null && post.getImagePath() != null) {
				// 업로드 성공하고 기존이미지있으면 서버에서 파일 제거
				fileManagerService.deleteFile(post.getImagePath());
			}
		}
		// db 업데이트
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
	}
}
