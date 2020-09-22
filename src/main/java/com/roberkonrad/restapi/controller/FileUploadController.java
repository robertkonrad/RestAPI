package com.roberkonrad.restapi.controller;

import com.roberkonrad.restapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/uploadFile")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        String saveFile = fileService.saveFile(file);
        Map<String, String> response = new HashMap<>();
        if (saveFile != null)  {
            response.put("message", "Success!" + " File "+ saveFile + " saved.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "File not saved.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }
}
