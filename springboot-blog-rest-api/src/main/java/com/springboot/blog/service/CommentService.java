package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> getAllComment(long postId);

    CommentDto getCommentById(long postId,long commentId);

    CommentDto updateCommentsById(long postId,long commentId,CommentDto commentDto);

    void deleteCommentById(long postId,long commentId);
}
