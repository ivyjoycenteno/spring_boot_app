package com.rijio.blog.controller;

import java.util.List;

import javax.validation.Valid;

import com.rijio.blog.payload.CommentDto;
import com.rijio.blog.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Comment Resources: CRUD REST APIs")
@RestController
@RequestMapping("/api/v1")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Create Comment REST API")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Fetch All Comments REST API")
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @ApiOperation(value = "Fetch Comment By Id REST API")
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(commentService.getCommentById(postId, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Comment By Id REST API")
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") long id, @Valid @RequestBody CommentDto commentRequest) {
        return new ResponseEntity<CommentDto>(commentService.updateComment(postId, id, commentRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Comment By Id REST API")
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") long id) {
        commentService.deleteComment(postId, id);
        return new ResponseEntity<>("Comment successfully deleted.", HttpStatus.OK);
    }

}
