package com.memo.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.post.postlist.Post;

@Mapper
public interface PostMapper {
	public List<Post> selectPostListByUserId(int userId);
	
	public void insertPost(
			@Param("subject")String subject, 
			@Param("content")String content
			,@Param("userId")int userId
			,@Param("imagePath") String imagePath
			,@Param("userLoginId") String userLoginId);
	
	public Post selectPostByuserIdpostId(
			@Param("userId")int userId, 
			@Param("postId")int postId);
}
