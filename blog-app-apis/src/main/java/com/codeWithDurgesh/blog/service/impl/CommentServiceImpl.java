package com.codeWithDurgesh.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithDurgesh.blog.constants.AppConstant;
import com.codeWithDurgesh.blog.entities.Comment;
import com.codeWithDurgesh.blog.entities.Post;
import com.codeWithDurgesh.blog.exceptions.ResourceNotFoundException;
import com.codeWithDurgesh.blog.payloads.CommentDto;
import com.codeWithDurgesh.blog.repositories.CommentRepo;
import com.codeWithDurgesh.blog.repositories.PostRepo;
import com.codeWithDurgesh.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + commentId));
		this.commentRepo.delete(comment);

	}

}
