package com.codeWithDurgesh.blog.repositories;

import com.codeWithDurgesh.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
