package com.rijio.blog.repository;

import java.util.List;

import com.rijio.blog.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByPostId(Long postId);
}
