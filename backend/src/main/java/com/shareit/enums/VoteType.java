package com.shareit.enums;

public enum VoteType {
    UP_VOTE ( "UP_VOTE" ),
    DOWN_VOTE ( "DOWN_VOTE" ),

    NO_VOTE ("NO_VOTE");

    private final String value;

    VoteType( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
