package com.memo.post.bo;

import java.util.Collections;
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
	
	//페이징 필드
	private static final int POST_MAX_SIZE = 3;
	
	//input : 로그인된 사람의 userId    output: List<Post>
	public List<Post> getPostListByUserId(int userId, Integer prevId, Integer nextId) {
		// 게시글 번호 10 9 8    7 6 5     4 3 2      1
		// 4 3 2 페이지에 있을때
		// 1) 다음: 2보다 작은 3개를 desc
		// 2) 이전: 4보다 큰 3개를 asc(5 6 7)  => List reverse = 7 6 5
		// 3) 페이징 정보 없음: 최신순 3개 desc
		Integer standardId = null; // 기준이 되는 postId
		String direction = null; // 방향
		if (prevId != null) { // 2.이전
			standardId = prevId;
			direction = "prev";
			
			List<Post> postList = postMapper.selectPostListByUserId(userId, standardId, direction, POST_MAX_SIZE);
			// reverse list 567 => 765
			Collections.reverse(postList);// 뒤집고 저장까지
			
			return postList;
		} else if (nextId != null) { // 1. 다음
			standardId = nextId;
			direction = "next";
		}
		
		// 3. 페이징정보 없을때, 1) 다음
		return postMapper.selectPostListByUserId(userId, standardId, direction,POST_MAX_SIZE);
	}
	// 이전 페이지의 마지막인가
	public boolean isPrevLastPageByUserId(int userId, int prevId) {
		int postId = postMapper.selectPostIdByUserIdSort(userId, "desc");
		return postId == prevId;
	}
	// 다음 페이지의 마지막인가
	public boolean isNextLastPageByUserId(int userId, int nextId) {
		int postId = postMapper.selectPostIdByUserIdSort(userId, "asc");
		return postId == nextId;
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
	
	public void deletePost(int deleteId,int userId) {
		// 기존글이 있는지 확인
		Post post = postMapper.selectPostByuserIdpostId(userId, deleteId);
		if (post == null) {
			log.info("[글 삭제] post is null. postId:{}, userId:{}", deleteId, userId);
			return;
		}
		
		// DB삭제
		int deleterowCount = postMapper.deletePost(deleteId);
		
		// 이미지가 존재한다면 파일까지 삭제. db 삭제도 성공했을시.
		if (deleterowCount > 0 && post.getImagePath() != null) {
			fileManagerService.deleteFile(post.getImagePath());
		}
	}
}
