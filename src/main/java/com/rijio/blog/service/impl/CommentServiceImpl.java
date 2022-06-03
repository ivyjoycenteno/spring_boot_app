package com.rijio.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.rijio.blog.entity.Comment;
import com.rijio.blog.entity.Post;
import com.rijio.blog.exception.BlogAPIException;
import com.rijio.blog.exception.ResourceNotFoundException;
import com.rijio.blog.payload.CommentDto;
import com.rijio.blog.repository.CommentRepository;
import com.rijio.blog.repository.PostRepository;
import com.rijio.blog.service.CommentService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;
            
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        // convert into entity
        Comment comment = mapToEntity(commentDto);

        // retrieve post entity id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // set post to comment entity
        comment.setPost(post);
 
        // save comment info
        Comment newComment = commentRepository.save(comment);

        // convert into DTO
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert entity list to dto
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long id) {
        // retrieve and validate comment entity of a post
        Comment comment = getValidatedComment(postId, id);

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long id, CommentDto commentRequest) {
        // retrieve and validate comment entity of a post
        Comment comment = getValidatedComment(postId, id);

        comment.setName(commentRequest.getName());
        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }
    
    @Override
    public void deleteComment(Long postId, long id) {
        // retrieve and validate comment entity of a post
        Comment comment = getValidatedComment(postId, id);

        commentRepository.delete(comment);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);

        return comment;
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        return commentDto;
    }

    private Comment getValidatedComment(Long postId, long id) {
        // retrieve post entity id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // check comment existence
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        if(comment.getPost().getId() != post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment not belong to the post");
        }

        return comment;
    }
}
