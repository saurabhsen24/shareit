package com.shareit.dto.request;

import com.shareit.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto extends PostDto {

    private String postUrl;
    private String communityName;
}
