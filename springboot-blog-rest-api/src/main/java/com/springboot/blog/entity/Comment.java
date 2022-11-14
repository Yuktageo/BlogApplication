package com.springboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name="email",nullable = false)
    private String email;
    @Column(name = "body",nullable = false)
    private String body;

    //LAZY loading is when you load the information whenever it's needed
    //EAGER is when you fetch immediately
    //LAZY loading fect type means that when someone calls getPost() it is loaded then
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;
}
