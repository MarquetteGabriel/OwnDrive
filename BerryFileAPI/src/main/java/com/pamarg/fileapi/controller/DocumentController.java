package com.pamarg.fileapi.controller;

import com.pamarg.fileapi.service.ResponseClass;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DocumentController {
    @Value("${file.repository.path}")
    private String fileRepositoryPath;

    @PostConstruct
    private void init() {
        System.out.println("File Repository Path: " + fileRepositoryPath);
    }

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
            String[] splittedFile = file.getName().split("\\.");
            String type = (splittedFile.length <= 1) ? "other" : splittedFile[splittedFile.length -1];

            return ResponseEntity.ok(new ResponseClass(fileName, downloadUrl, type, file.getSize(), null));
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
                String[] splittedFile = file.getName().split("\\.");
                String type = (splittedFile.length <= 1) ? "other" : splittedFile[splittedFile.length -1];

                responseList.add(new ResponseClass(fileName, downloadUrl, type, file.getSize(), null));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseClass>> getAllFilesFromFileSystem() {
        return getAllFilesFromPath("");
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
                String[] splittedFile = file.getName().split("\\.");
                String type = (splittedFile.length <= 1) ? (isDossier(file) ? "rep" : "???") : splittedFile[splittedFile.length -1];
                Date modifiedAt = new Date(file.lastModified());

                return new ResponseClass(file.getName(), downloadURL, type, file.length(), modifiedAt);
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(responseClasses);
    }

    private boolean isDossier(File file){
        return (file.length() == 0);
    }
}
