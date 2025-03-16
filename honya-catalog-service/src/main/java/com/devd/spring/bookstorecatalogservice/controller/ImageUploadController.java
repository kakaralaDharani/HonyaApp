package com.devd.spring.bookstorecatalogservice.controller;

import com.devd.spring.bookstorecommons.exception.RunTimeExceptionPlaceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Devaraj Reddy - 21-Dec-2020
 */
/*
    Lot of improvement s can be done in image upload section.
    We can use AWS s3 to store images instead of this.
    For time being will go with no-cost implementation.
 */
@RestController
@Slf4j
public class ImageUploadController {

    @PostMapping("image/upload")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        if (file == null) {
            throw new RunTimeExceptionPlaceHolder("Invalid Image!!");
        }
        UUID uuid = UUID.randomUUID();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Files.createDirectories(Paths.get("images"));
        Path path = Paths.get("images/" + uuid.toString() + "__" + fileName);
        Path absolutePath = path.toAbsolutePath();
        try {
            Files.copy(file.getInputStream(), absolutePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> response = new HashMap<>();
        response.put("imageId", uuid.toString() + "__" + fileName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }	
    
    @GetMapping(path = "image/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable String imageId) throws IOException {
        try {
            //Path imagePath = Paths.get("images", imageId);            
            Path imagePath = Paths.get(new File("images").getAbsolutePath(), imageId);
            log.info("file getRoot : " + imagePath.getRoot());
            log.info("file getFileSystem : " + imagePath.getFileSystem());
            log.info("file path : " + imagePath);
            log.info("file CanonicalPath : " + Paths.get(new File("images").getCanonicalPath(), imageId));
            log.info("file AbsolutePath : " + Paths.get(new File("images").getAbsolutePath(), imageId));
            log.info("file exists: " + Files.exists(imagePath) + ", readable: " + Files.isReadable(imagePath));
            
            // Check if the file exists and is readable
            if (Files.exists(imagePath) && Files.isReadable(imagePath)) {
                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(imagePath));
                
                // Detect file type dynamically
                String contentType = Files.probeContentType(imagePath);
                if (contentType == null) {
                    contentType = "application/octet-stream"; // Fallback content type
                }
                
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .contentLength(resource.contentLength())
                        .body(resource);
            }
            
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Exception in getImage: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving image");
        }
    }

}
