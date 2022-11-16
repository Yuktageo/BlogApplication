package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private  long id;
    @NotEmpty(message = "Minimum size for the field name should be 2")
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private  String email;
    @NotEmpty
    @Size(min=10,message = "Comment body must be 10 Character")
    private String body;
}
