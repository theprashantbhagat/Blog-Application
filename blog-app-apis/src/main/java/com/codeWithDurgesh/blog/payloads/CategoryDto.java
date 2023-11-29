package com.codeWithDurgesh.blog.payloads;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDto {

	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4,message = "Category title should contain atleast 4 characters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10,message = "Category description should contain minimum 10 charaters")
	private String categoryDescription;
	
}
