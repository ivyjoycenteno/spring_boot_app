package com.rijio.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
    private long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email
    private String email;

    @NotEmpty(message = "Body content is required")
    @Size(min = 10, message = "Should at least 10 characters")
    private String body;
     
}
