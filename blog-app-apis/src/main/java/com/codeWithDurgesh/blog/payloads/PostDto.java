package com.codeWithDurgesh.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private Integer postId;

	@NotEmpty
	@Size(min = 4, message = "Title should be minimum of 4 characters")
	private String title;

	@NotEmpty
	@Size(min = 10, message = "Content should be minimum of 10 characters")
	private String content;

	private String imageName;

	private Date postDate;

	private UserDto user;

	private CategoryDto category;
	
	private Set<CommentDto> comments=new HashSet<>();
}
