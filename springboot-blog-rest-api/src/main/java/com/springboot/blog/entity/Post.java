package com.springboot.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//Hibernate internally uses proxies to generate objects
//Entity represents a table in the relational database and each entity instance corresponds to the row in the table
@Entity
@Table(
        name="posts",uniqueConstraints = {@UniqueConstraint(columnNames={"title"})}
)
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "content",nullable = false)
    private String content;

    //Cascade type all means that whenever you are saving the parent you need to saved the child too
    //entity state transition should propogate from parent to child and thus cascade type should be defined inside the oneto many
    //orphan removal means that whenever you are removing the parent you need to remove the child too
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments=new HashSet<>();
}
