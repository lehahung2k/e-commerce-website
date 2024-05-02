package com.hunglh.backend.services.impl;

import com.hunglh.backend.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/upload/images";
    public static String storeImageUrl = "http://localhost:8080/api" + "/upload/images/";

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return storeImageUrl + "default.png";
        }
        String randomFileName = generateRandomFileName(file);
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, randomFileName);
        Files.write(fileNameAndPath, file.getBytes());
        return storeImageUrl + randomFileName;
    }

    @Override
    public ResponseEntity<Resource> getImage(String imageName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIRECTORY).resolve(imageName).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public void deleteImage(String fileName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName.substring(storeImageUrl.length()));
        Files.delete(filePath);
    }

    private String generateRandomFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }
}
