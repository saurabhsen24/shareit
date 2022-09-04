package com.shareit.dto.response;

import com.shareit.dto.PostDto;
import com.shareit.dto.projection.PostProjection;
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

    private String postUrl;
    private Integer voteCount;

    private String userName;

    @Builder
    public PostResponseDto(Long postId ,String postTitle, String postDescription, String postUrl, Integer voteCount,
                           String userName) {
        super(postTitle,postDescription);
        this.postId = postId;
        this.postUrl = postUrl;
        this.voteCount = voteCount;
        this.userName = userName;
    }

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .postDescription(post.getPostDescription())
                .voteCount(post.getVoteCount())
                .build();

    }

    public static PostResponseDto from(PostProjection postProjection) {
        return PostResponseDto.builder()
                .postId(postProjection.getPostId())
                .postTitle(postProjection.getPostTitle())
                .postDescription(postProjection.getPostDescription())
                .postUrl(postProjection.getPostUrl())
                .voteCount(postProjection.getVoteCount())
                .userName(postProjection.getUserName())
                .build();

    }
}
