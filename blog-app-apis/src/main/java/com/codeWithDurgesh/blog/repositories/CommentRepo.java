package com.codeWithDurgesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeWithDurgesh.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
