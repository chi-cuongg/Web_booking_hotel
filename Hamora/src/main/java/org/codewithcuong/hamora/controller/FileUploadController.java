package org.codewithcuong.hamora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.codewithcuong.hamora.service.CloudinaryService;

import java.io.IOException;
import java.util.Map;

// FileUploadController.java
@RestController
@RequestMapping("/api/files")
public class FileUploadController {


    private final CloudinaryService cloudinaryService;

    public FileUploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", required = false) String folderName) throws IOException {

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }


        Map result = cloudinaryService.uploadImage(file, folderName);
        String imageUrl = (String) result.get("secure_url"); // or "url" if you want HTTP

        return ResponseEntity.ok(Map.of(
                "message", "Upload successful",
                "url", imageUrl
        ));
    }



}
