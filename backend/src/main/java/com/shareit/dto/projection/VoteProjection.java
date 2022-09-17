package com.shareit.dto.projection;

import com.shareit.enums.VoteType;

public interface VoteProjection {

    Long getPostId();
    String getUserName();
    VoteType getVoteType();
}
