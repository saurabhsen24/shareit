package com.shareit.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/")
    public String test(){
        return "ShareIt Apis";
    }
}
