package com.codeWithDurgesh.blog.service;

import com.codeWithDurgesh.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	void deleteComment(Integer commentId);
}
