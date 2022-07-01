package com.shareit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @NotBlank(message = "Post Title cannot be empty")
    private String postTitle;

    @NotBlank(message = "Post Description should be present")
    private String postDescription;

}
