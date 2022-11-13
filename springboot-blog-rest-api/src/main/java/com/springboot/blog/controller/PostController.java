package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/posts")
public class PostController {

    //We are injecting the inteface over here not the class that means that we are doing the loose coupling over here
    private PostService postService;

    //constructor injection
    public PostController(PostService postService){
        this.postService=postService;
    }

    //create a blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all post API
    @GetMapping
    public List<PostDto> getAllPost(){
        return postService.getAllPost();
    }



}
