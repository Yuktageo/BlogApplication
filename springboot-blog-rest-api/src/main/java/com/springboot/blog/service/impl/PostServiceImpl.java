package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostResponse getAllPost(int pageNo,int  pageSize,String sortBy,String sortDir) {
        //create a pageable instance
        //Pageable represents the set of pages to be printed given the pageNo and pageSize. Pageable object returns the total number of pages in the set.
        //This of method now supports both pagination and sorting
        //for descending order you can do Sort.by(sortBy).descending

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts=postRepository.findAll(pageable);

        //get content for page object

        List<Post> listOfPosts=posts.getContent();

        PostResponse postResponse=new PostResponse();

        List<PostDto> content=listOfPosts.stream().map(this::mapToDto).collect(Collectors.toList());
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
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

    @Override
    public void deletePostById(long id) {

        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));

        postRepository.delete(post);


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
