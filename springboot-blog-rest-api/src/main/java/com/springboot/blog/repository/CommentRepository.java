package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

//Simple JPA Repository class implements the JPA Repository
//In JpaRepository you need to send the entity type that is Comment and id type of comment is long
//Repository kayer requires an application level transaction but this transaction should progate from service layerv
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
