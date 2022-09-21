package com.shareit.controller;

import com.shareit.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TestController {

    @Autowired
    FileUploadService fileUploadService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/")
    public String test(){
        return "ShareIt Apis";
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileUploadService.uploadFile(file));
    }
}
