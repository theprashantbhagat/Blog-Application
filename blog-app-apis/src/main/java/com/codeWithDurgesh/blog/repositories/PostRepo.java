package com.codeWithDurgesh.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeWithDurgesh.blog.entities.Category;
import com.codeWithDurgesh.blog.entities.Post;
import com.codeWithDurgesh.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
	
}
