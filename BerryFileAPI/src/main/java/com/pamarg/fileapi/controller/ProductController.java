package com.pamarg.fileapi.controller;

import com.pamarg.fileapi.service.ProductService;
import com.pamarg.fileapi.service.ResponseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService fileService;
    @Value("${file.repository.path}")
    private String fileRepositoryPath;

    //for uploading the SINGLE file to the File System
    @PostMapping("/single")
    public ResponseEntity<ResponseClass> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            file.transferTo(new File(fileRepositoryPath + fileName));
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileName)
                    .toUriString();
            ResponseClass response = new ResponseClass(fileName,
                    downloadUrl,
                    file.getContentType(),
                    file.getSize());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //for uploading the MULTIPLE file to the File system
    @PostMapping("/multiple")
    public ResponseEntity<List<ResponseClass>> handleMultipleFilesUpload(@RequestParam("files") MultipartFile[] files) {
        List<ResponseClass> responseList = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            try {
                file.transferTo(new File(fileRepositoryPath + fileName));
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(fileName)
                        .toUriString();
                ResponseClass response = new ResponseClass(fileName,
                        downloadUrl,
                        file.getContentType(),
                        file.getSize());
                responseList.add(response);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseClass>> getAllFilesFromFileSystem() {
        File folder = new File(fileRepositoryPath);
        File[] files = folder.listFiles();

        if (files == null) {
            return ResponseEntity.notFound().build();
        }

        List<ResponseClass> responseClasses = Arrays.stream(files)
                .map(file -> {
                    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/download/")
                            .path(file.getName())
                            .toUriString();
                    return new ResponseClass(file.getName(),
                            downloadURL,
                            MediaType.APPLICATION_OCTET_STREAM_VALUE, // You may need to adjust the content type based on your requirements
                            file.length());
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responseClasses);
    }

    @GetMapping("/getFromPath")
    public ResponseEntity<List<ResponseClass>> getAllFilesFromPath(@RequestParam("path") String relativePath) {
        String fullPath = fileRepositoryPath + File.separator + relativePath;
        File folder = new File(fullPath);
        File[] files = folder.listFiles();

        if (files == null) {
            return ResponseEntity.notFound().build();
        }

        List<ResponseClass> responseClasses = Arrays.stream(files)
                .map(file -> {
                    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/download/")
                            .path(relativePath + "/" + file.getName()) // Include the relative path in the download URL
                            .toUriString();
                    return new ResponseClass(file.getName(),
                            downloadURL,
                            MediaType.APPLICATION_OCTET_STREAM_VALUE, // Adjust content type based on your requirements
                            file.length());
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responseClasses);
    }
}
