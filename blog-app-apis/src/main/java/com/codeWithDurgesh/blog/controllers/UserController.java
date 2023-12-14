package com.codeWithDurgesh.blog.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.codeWithDurgesh.blog.payloads.UserDto;
import com.codeWithDurgesh.blog.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * @apiNote this is use to create users
	 * @author Prashant
	 * @since 1.0
	 * @param userDto
	 * @return
	 */
	@PostMapping("/user")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		log.info("Entering request for save user data");
		UserDto createUserDto = this.userService.createUser(userDto);
		System.out.println(createUserDto);
		log.info("Completed request for save user data");
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	/**
	 * @apiNote this is use to update the existing user
	 * @author Prashant
	 * @since V 1.0
	 * @param userDto
	 * @param userId
	 * @return
	 */
	@PutMapping("/user/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		log.info("Entering request for update the user data");
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		log.info("Completed request for update the user data");
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to delete the existing particular record from database
	 * @author Prashant
	 * @since V 1.0
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		log.info("Entering request for delete the user data with userId {} :" + userId);
		this.userService.deleteUser(userId);
		log.info("Completed request for delete the user data with userId {} :" + userId);
		return new ResponseEntity<>(new ApiResponse(AppConstant.DELETE_RESPONSE, true), HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to get all the records from database
	 * @author Prashant
	 * @since V 1.0
	 * @return
	 */
	@GetMapping("/user")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		log.info("Entering request for get all the user data");
		List<UserDto> allUsers = this.userService.getAllUsers();
		log.info("Completed request for get all the user data");
		return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.OK);
	}

	/**
	 * @apiNote this is use to get one record from database
	 * @author Prashant
	 * @since V 1.0
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		log.info("Entering request for get the user data with userId {} :" + userId);
		UserDto userById = this.userService.getUserById(userId);
		log.info("Completed request for get the user data with userId {} :" + userId);
		return new ResponseEntity<UserDto>(userById, HttpStatus.OK);
	}

}
