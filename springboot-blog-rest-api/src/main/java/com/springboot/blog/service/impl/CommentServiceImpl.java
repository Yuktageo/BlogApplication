package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public List<CommentDto> getAllComment(long postId) {

        List<Comment> commentList=commentRepository.findByPostId(postId);
        return commentList.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
    }


    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        //get the post by id

        Post postFromEntity=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));


        //get the comment by id

        Comment commentFromEntity=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));

        //check wether the comment belong to that particular post or not

        if(!Objects.equals(commentFromEntity.getPost().getId(), postFromEntity.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment do not belong to this post");
        }

        return  mapToDto(commentFromEntity);
    }

    @Override
    public CommentDto updateCommentsById(long postId, long commentId,CommentDto commentDto) {
        Post postFromEntity=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));

        //get the comment by id

        Comment commentFromEntity=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));


        if(Objects.equals(commentFromEntity.getPost().getId(), postFromEntity.getId())){
            commentFromEntity.setBody(commentDto.getBody());
            commentFromEntity.setName(commentDto.getName());
            commentFromEntity.setEmail(commentDto.getEmail());

            commentRepository.save(commentFromEntity);
        }else{
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment do not belong to this post");
        }

       return mapToDto(commentFromEntity);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {

        Post postFromEntity=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));

        //get the comment by id

        Comment commentFromEntity=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));


        if(Objects.equals(commentFromEntity.getPost().getId(), postFromEntity.getId())){
            commentRepository.delete(commentFromEntity);
        }else {
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"Comment do not belong to this post");
        }


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
