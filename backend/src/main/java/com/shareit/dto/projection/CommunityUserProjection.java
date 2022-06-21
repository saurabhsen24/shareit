package com.shareit.dto.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommunityUserProjection {

    private String userId;
    private String userName;
    private String email;
    private String profilePicUrl;
}
