package com.codeWithDurgesh.blog.service;

import java.util.List;

import com.codeWithDurgesh.blog.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto ,Integer categoryId);
	
	CategoryDto getSingelCategory(Integer categoryId);
	
	List<CategoryDto> getAllCategories();
	
	void deleteCategory(Integer categoryId);
}
