package com.codeWithDurgesh.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;

	@NotEmpty
	@Size(min = 4, message = "Username must be of 4 characters!!")
	private String name;

	@Email(message = "user email address is not valid !!")
	private String email;

	@NotEmpty
	@Size(min = 3, max = 10, message = "user password must be in between 3 to 10")
	private String password;

	@NotEmpty
	private String about;

	private Set<RoleDto> roles=new HashSet<>();
}
