package com.rijio.blog.repository;

import java.util.Optional;

import com.rijio.blog.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(String name);
}