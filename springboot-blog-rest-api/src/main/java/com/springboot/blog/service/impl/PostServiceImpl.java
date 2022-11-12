package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    //Whenever there is only one private field we do not need to use @AutoWire

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    public PostDto createPost(PostDto postDto){

        //converting the DTO into entity

        Post post=new Post();

        post.setTitle(postDto.getTitle());
        post.setId(postDto.getId());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        //newPost contains the saved post in the databse
        Post newPost=postRepository.save(post);

        //convert entity back to DTO

        PostDto postResponse=new PostDto();

        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());


        return null;
    };
}
