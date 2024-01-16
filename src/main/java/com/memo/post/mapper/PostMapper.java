package com.memo.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.memo.post.postlist.Post;

@Mapper
public interface PostMapper {
	public List<Post> selectPostListByUserId(int userId);
}
