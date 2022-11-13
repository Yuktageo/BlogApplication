package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    //Whenever there is only one private field we do not need to use @AutoWire
    //constructor injection
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    public PostDto createPost(PostDto postDto){

        //converting the DTO into entity

       Post post=mapToEntity(postDto);

        //newPost contains the saved post in the database
        Post newPost=postRepository.save(post);

        //convert entity back to DTO

        PostDto postResponse=mapToDto(newPost);


        return postResponse;
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts=postRepository.findAll();

        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //get the post by id from the data base and if not available throw the exception

        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));

        //post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost=postRepository.save(post);
        return mapToDto(updatedPost);
    }

    //convert the entity to the dto inside this method

    private PostDto mapToDto(Post post){
        PostDto postDto=new PostDto();

        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        postDto.setTitle(post.getTitle());

        return postDto;
    }

    //Convert the map back to the entity
    private Post mapToEntity(PostDto postDto){
        Post post=new Post();

        post.setTitle(postDto.getTitle());
        post.setId(postDto.getId());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }
}
