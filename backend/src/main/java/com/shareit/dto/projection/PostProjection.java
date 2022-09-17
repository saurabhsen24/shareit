package com.shareit.dto.projection;

public interface PostProjection {

    Long getPostId();
    String getPostTitle();
    String getPostDescription();
    String getPostUrl();
    Integer getVoteCount();

    String getUserName();

}
