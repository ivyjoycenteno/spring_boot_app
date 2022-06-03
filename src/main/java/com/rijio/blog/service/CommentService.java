package com.rijio.blog.service;

import java.util.List;

import com.rijio.blog.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(Long postId);

    CommentDto getCommentById(Long postId, Long id);

    CommentDto updateComment(Long postId, long id, CommentDto commentRequest);

    void deleteComment(Long postId, long id);
}
