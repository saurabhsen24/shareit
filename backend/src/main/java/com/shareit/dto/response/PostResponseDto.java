package com.shareit.dto.response;

import com.shareit.dto.PostDto;
import com.shareit.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto extends PostDto {

    private Long postId;
    private Integer voteCount;

    @Builder
    public PostResponseDto(Long postId ,String postTitle, String postDescription, Integer voteCount) {
        super(postTitle,postDescription);
        this.postId = postId;
        this.voteCount = voteCount;
    }

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .postDescription(post.getPostDescription())
                .voteCount(post.getVoteCount())
                .build();

    }
}
