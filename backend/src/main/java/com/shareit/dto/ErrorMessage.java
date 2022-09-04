package com.shareit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessage {

    private Integer statusCode;
    private String message;
    private String description;
    private Date timestamp;

}
