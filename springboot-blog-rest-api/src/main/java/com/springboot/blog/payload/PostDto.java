package com.springboot.blog.payload;

import lombok.Data;

//RequestBody for converting the JSON into the java object by using http message converter
@Data
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;

}