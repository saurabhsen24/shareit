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
    public PostResponseDto(String postName, String postDescription, Long postId, Integer voteCount) {
        super(postName,postDescription);
        this.postId = postId;
        this.voteCount = voteCount;
    }

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .postId(post.getPostId())
                .postName(post.getPostName())
                .postDescription(post.getPostDescription())
                .voteCount(post.getVoteCount())
                .build();

    }
}
