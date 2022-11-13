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

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("id") long id){
        PostDto postResponse=postService.updatePost(postDto,id);

        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }



}
