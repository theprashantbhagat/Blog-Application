package com.codeWithDurgesh.blog;

import com.codeWithDurgesh.blog.constants.AppConstant;
import com.codeWithDurgesh.blog.entities.Role;
import com.codeWithDurgesh.blog.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(this.passwordEncoder.encode("abc"));

		try {
			Role role1 = new Role();
			role1.setRoleId(AppConstant.ADMIN_USER);
			role1.setRoleName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setRoleId(AppConstant.NORMAL_USER);
			role2.setRoleName("ROLE_NORMAL");

			List<Role> roles = List.of(role1, role2);
			List<Role> result = this.roleRepository.saveAll(roles);
			result.forEach(r ->
					{
						System.out.println(r.getRoleName());
					}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
}
