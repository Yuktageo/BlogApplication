package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


//By extending the JPA repository you get all the CTUD operation for talking with the datbase
public interface PostRepository extends JpaRepository<Post,Long> {
}
