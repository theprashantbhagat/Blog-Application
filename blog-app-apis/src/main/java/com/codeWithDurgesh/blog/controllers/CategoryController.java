package com.codeWithDurgesh.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithDurgesh.blog.constants.AppConstant;
import com.codeWithDurgesh.blog.payloads.ApiResponse;
import com.codeWithDurgesh.blog.payloads.CategoryDto;
import com.codeWithDurgesh.blog.service.CategoryService;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * @apiNote this is use to create category in the database
	 * @author prashant
	 * @since V 1.0
	 * @param categoryDto
	 * @return
	 */
	@PostMapping("/categories")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		log.info("Entering request to create category");
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		log.info("Completed request to create category");
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	/**
	 * @apiNote this is use to update existing record in the database
	 * @author prashant
	 * @since V 1.0
	 * @param categoryDto
	 * @param catId
	 * @return
	 */
	@PutMapping("/categories/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer catId) {
		log.info("Entering request to update category with catId {} :"+catId);
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);
		log.info("Completed request to create category with catId {} :"+catId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to get category by using Id from database
	 * @author prashant
	 * @since V 1.0
	 * @param catId
	 * @return
	 */
	@GetMapping("/categories/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		log.info("Entering request for get category with catId {} :"+catId);
		CategoryDto category = this.categoryService.getSingelCategory(catId);
		log.info("Completed request for get category with catId {} :"+catId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to get all categories from database
	 * @author prashant
	 * @since V 1.0
	 * @return
	 */
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		log.info("Entering request for get all categories");
		List<CategoryDto> allCategories = this.categoryService.getAllCategories();
		log.info("Completed request for get all categories");
		return new ResponseEntity<List<CategoryDto>>(allCategories, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to delete record from the database by using Id
	 * @author prashant
	 * @since V 1.0
	 * @param catId
	 * @return
	 */
	@DeleteMapping("/categories/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		log.info("Entering request for delete category with catId {} :"+catId);
		this.categoryService.deleteCategory(catId);
		log.info("Completed request for delete category with catId {} :"+catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstant.DELETE_RESPONSE, true), HttpStatus.OK);
	}

}
