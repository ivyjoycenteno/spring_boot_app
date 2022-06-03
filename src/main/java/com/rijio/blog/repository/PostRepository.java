package com.rijio.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rijio.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
