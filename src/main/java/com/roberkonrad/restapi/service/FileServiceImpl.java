package com.roberkonrad.restapi.service;

import com.roberkonrad.restapi.configuration.FileUploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileUploadConfig fileUploadConfig;

    @Override
    public String saveFile(MultipartFile file) {
        Path folder = Paths.get(fileUploadConfig.getUploadFolder()).normalize().toAbsolutePath();
        Path destination = Paths.get(folder.toString() + "\\" + file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Resource getFile(String fileName) {
        Path path = Paths.get(fileUploadConfig.getUploadFolder() + "\\" + fileName).normalize().toAbsolutePath();
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
