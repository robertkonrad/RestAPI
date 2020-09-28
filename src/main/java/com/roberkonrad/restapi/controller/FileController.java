package com.roberkonrad.restapi.controller;

import com.roberkonrad.restapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/uploadFile")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        String saveFile = fileService.saveFile(file);
        Map<String, String> response = new HashMap<>();
        if (saveFile != null) {
            response.put("message", "Success!" + " File " + saveFile + " saved.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "File not saved.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(value = "/downloadFile/{fileName:.+}")
    public ResponseEntity downloadFile(@PathVariable String fileName) {
        Map<String, String> response = new HashMap<>();
        String contentType = "application/octet-stream";
        Resource resource = fileService.getFile(fileName);
        if (!resource.exists()) {
            response.put("message", "File doesn't exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
