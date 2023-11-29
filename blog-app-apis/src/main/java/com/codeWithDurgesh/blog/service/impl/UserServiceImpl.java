package com.codeWithDurgesh.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.codeWithDurgesh.blog.entities.Role;
import com.codeWithDurgesh.blog.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codeWithDurgesh.blog.constants.AppConstant;
import com.codeWithDurgesh.blog.entities.User;
import com.codeWithDurgesh.blog.exceptions.ResourceNotFoundException;
import com.codeWithDurgesh.blog.payloads.UserDto;
import com.codeWithDurgesh.blog.repositories.UserRepo;
import com.codeWithDurgesh.blog.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		//encode the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//roles
		Role role = this.roleRepository.findById(AppConstant.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser,UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		log.info("Initializing dao call for save user data");
		User user = this.modelMapper.map(userDto, User.class);
		User addedUser = this.userRepo.save(user);
		log.info("completed dao call for save user data");
		return this.modelMapper.map(addedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		log.info("Initiating dao call for update user data with userId {} :" + userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);
		log.info("Completed dao call for update user data with userId {} :" + userId);
		return this.modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		log.info("Initiating dao call for getting user data with userId {} :"+userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));
		log.info("Completed dao call for getting user data with userId {} :"+userId);
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		log.info("Initiating dao call for getting all user data");
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao call for getting all user data");
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		log.info("Initiating dao call for delete user data with userId {} :"+userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));
		log.info("Completed dao call for delete user data with userId {} :"+userId);
		this.userRepo.delete(user);

	}

}
