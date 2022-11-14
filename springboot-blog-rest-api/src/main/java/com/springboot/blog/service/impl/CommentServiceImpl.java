package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    //constructor injection
    public  CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository){
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment commentResponse=mapToEntity(commentDto);

        //find the post by id

        Post post=postRepository.findById(postId).orElseThrow(()->new  ResourceNotFoundException("Post","id",postId));

        //set the post to comment entity
        commentResponse.setPost(post);

        //comment entity to db

        Comment newComment=commentRepository.save(commentResponse);






        return mapToDto(newComment);
    }

    private CommentDto mapToDto(Comment comment){

        CommentDto commentDto=new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=new Comment();

        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());

        return comment;
    }
}
