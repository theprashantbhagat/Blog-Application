package com.codeWithDurgesh.blog.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeWithDurgesh.blog.constants.AppConstant;
import com.codeWithDurgesh.blog.constants.PaginationConstants;
import com.codeWithDurgesh.blog.payloads.ApiResponse;
import com.codeWithDurgesh.blog.payloads.PostDto;
import com.codeWithDurgesh.blog.payloads.PostResponse;
import com.codeWithDurgesh.blog.service.FileService;
import com.codeWithDurgesh.blog.service.PostService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	/**
	 * @apiNote this is use to create post based on the user and category
	 * @author Prashant
	 * @since V 1.0
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		log.info("Entering request for create post with userId and categoryId {} :" + userId + " " + categoryId);
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		log.info("Completed request for create post with userId and categoryId {} :" + userId + " " + categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	/**
	 * @apiNote this is use to update existing post based on postId
	 * @author Prashant
	 * @since V 1.0
	 * @param postDto
	 * @param postId
	 * @return
	 */
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		log.info("Entering request for update post with postId {} :" + postId);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		log.info("Completed request for update post with postId {} :" + postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to get post based on userId
	 * @author Prashant
	 * @since V 1.0
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		log.info("Entering request for get post with userId {} :" + userId);
		List<PostDto> postsByUsers = this.postService.getPostByUser(userId);
		log.info("Completed request for get post with userId {} :" + userId);
		return new ResponseEntity<List<PostDto>>(postsByUsers, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to get post based on categoryId
	 * @author Prashant
	 * @since V 1.0
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		log.info("Entering request for get post with categoryId {} :" + categoryId);
		List<PostDto> postsByCat = this.postService.getPostByCategory(categoryId);
		log.info("Completed request for get post with categoryId {} :" + categoryId);
		return new ResponseEntity<List<PostDto>>(postsByCat, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to get all posts from database
	 * @author Prashant
	 * @since V 1.0
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNum", defaultValue = PaginationConstants.PAGE_NUMBER, required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = PaginationConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = PaginationConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = PaginationConstants.SORT_DIR, required = false) String sortDir) {
		log.info("Entering request for get all post");
		PostResponse posts = this.postService.getAllPost(pageNum, pageSize, sortBy, sortDir);
		log.info("Completed request for get all post");
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to get post based on postId
	 * @author Prashant
	 * @since V 1.0
	 * @param postId
	 * @return
	 */
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId) {
		log.info("Entering request for get post with postId {} :" + postId);
		PostDto post = this.postService.getPostById(postId);
		log.info("Completed request for get post with postId {} :" + postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to delete post based on postId
	 * @author Prashant
	 * @since V 1.0
	 * @param postId
	 * @return
	 */
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		log.info("Entering request for delete post with postId {} :" + postId);
		this.postService.deletePost(postId);
		log.info("Completed request for delete post with postId {} :" + postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstant.DELETE_RESPONSE, true), HttpStatus.OK);
	}

	/**
	 * @apiNote to search the posts based on Title
	 * @author Prashant
	 * @since V 1.0
	 * @param keywords
	 * @return
	 */
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String keywords) {
		log.info("Entering request for search post with keywords {} :" + keywords);
		List<PostDto> searchPosts = this.postService.searchPosts(keywords);
		log.info("Completed request for search post with keywords {} :" + keywords);
		return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK);
	}

	// postImage upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam MultipartFile image, @PathVariable Integer postId)
			throws IOException {

		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	// method to serve file
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {

		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}

}
