package com.hunglh.backend.services;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String uploadImage(MultipartFile file) throws IOException;
    ResponseEntity<Resource> getImage(String imageName) throws IOException;

    void deleteImage(String imageName) throws IOException;

}
