package com.codeWithDurgesh.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private Integer commentId;

	@NotBlank
	@Size(min = 7, message = "content should be of minimum 7 characters")
	private String content;
}
