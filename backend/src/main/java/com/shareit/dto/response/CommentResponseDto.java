package com.shareit.dto.response;

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

    private String commentText;
    private String userName;

    public static CommentResponseDto from(Comment comment) {
        String userName = JwtHelper.getCurrentLoggedInUsername();

        return CommentResponseDto.builder()
                .commentText(comment.getText())
                .userName(userName)
                .build();
    }
}
