package com.codeWithDurgesh.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithDurgesh.blog.constants.AppConstant;
import com.codeWithDurgesh.blog.entities.Category;
import com.codeWithDurgesh.blog.exceptions.ResourceNotFoundException;
import com.codeWithDurgesh.blog.payloads.CategoryDto;
import com.codeWithDurgesh.blog.repositories.CategoryRepo;
import com.codeWithDurgesh.blog.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		log.info("Initiating dao call for creating category");
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		log.info("Completed dao call for creating category");
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		log.info("Initiating dao call for updating category with id {} :"+categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + categoryId));

		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updetedCat = this.categoryRepo.save(cat);
		log.info("Completed dao call for updating category with id {} :"+categoryId);
		return this.modelMapper.map(updetedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto getSingelCategory(Integer categoryId) {
		log.info("Initiating dao call for get category with id {} :"+categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + categoryId));
		log.info("Completed dao call for get category with id {} :"+categoryId);
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		log.info("Initiating dao call for get all category");
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> catDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao call for get all category");
		return catDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		log.info("Initiating dao call for delete category with id {} :"+categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + categoryId));
		log.info("Completed dao call for delete category with id {} :"+categoryId);
		this.categoryRepo.delete(cat);

	}

}
