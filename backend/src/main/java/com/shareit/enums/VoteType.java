package com.shareit.enums;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    private Integer value;

    VoteType(Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
