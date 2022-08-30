package com.shareit.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TimeExpiredException extends RuntimeException {

    public TimeExpiredException( String message ) {
        super( message );
    }
}
