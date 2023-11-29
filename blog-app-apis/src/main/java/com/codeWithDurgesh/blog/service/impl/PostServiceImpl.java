package com.codeWithDurgesh.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codeWithDurgesh.blog.constants.AppConstant;
import com.codeWithDurgesh.blog.entities.Category;
import com.codeWithDurgesh.blog.entities.Post;
import com.codeWithDurgesh.blog.entities.User;
import com.codeWithDurgesh.blog.exceptions.ResourceNotFoundException;
import com.codeWithDurgesh.blog.payloads.PostDto;
import com.codeWithDurgesh.blog.payloads.PostResponse;
import com.codeWithDurgesh.blog.repositories.CategoryRepo;
import com.codeWithDurgesh.blog.repositories.PostRepo;
import com.codeWithDurgesh.blog.repositories.UserRepo;
import com.codeWithDurgesh.blog.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		log.info("Initiating dao call for create post with userId and categoryId {} :" + userId + " " + categoryId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setPostDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);
		log.info("Completed dao call for create post with userId and categoryId {} :" + userId + " " + categoryId);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		log.info("Initiating dao call for update post with postId {} :" + postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		log.info("Completed dao call for update post with postId {} :" + postId);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		log.info("Initiating dao call for dekete post with postId {} :" + postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + postId));
		log.info("Completed dao call for delete post with postId {} :" + postId);
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNum, Integer pageSize, String sortBy, String sortDir) {
		log.info("Initiating dao call for get all posts");
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable page = PageRequest.of(pageNum, pageSize, sort);
		Page<Post> findAll = this.postRepo.findAll(page);
		List<Post> posts = findAll.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(findAll.getNumber());
		postResponse.setPageSize(findAll.getSize());
		postResponse.setTotalElements(findAll.getTotalElements());
		postResponse.setTotalPages(findAll.getTotalPages());
		postResponse.setLastPage(findAll.isLast());
		log.info("Completed dao call for get all posts");
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		log.info("Initiating dao call for get post by postId {} :" + postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + postId));
		log.info("Completed dao call for get post by postId {} :" + postId);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		log.info("Initiating dao call for get post by categoryId {} :" + categoryId);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao call for get post by categoryId {} :" + categoryId);
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		log.info("Initiating dao call for get post by userId {} :" + userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao call for get post by userId {} :" + userId);
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		log.info("Initiating dao call for search posts with keyword {} :"+keyword);
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		log.info("Completed dao call for search posts with keyword {} :"+keyword);
		return postDtos;
	}

}
