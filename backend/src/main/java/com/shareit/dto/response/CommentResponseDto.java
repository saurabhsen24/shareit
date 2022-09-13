package com.shareit.dto.response;

import com.shareit.dto.projection.CommentProjection;
import com.shareit.entities.Comment;
import com.shareit.utils.JwtHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long commentId;
    private String commentText;
    private String userName;

    public static CommentResponseDto from(CommentProjection comment) {

        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .commentText(comment.getText())
                .userName(comment.getUserName())
                .build();
    }
}
