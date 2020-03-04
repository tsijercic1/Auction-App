package com.github.tsijercic1.auctionapi.services;

import com.github.tsijercic1.auctionapi.configuration.FileStorageConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FileStorageService {
    final FileStorageConfiguration fileStorageConfiguration;
    private Path resourcePath;

    public FileStorageService(FileStorageConfiguration fileStorageConfiguration) {
        this.fileStorageConfiguration = fileStorageConfiguration;
        resourcePath = Paths.get(fileStorageConfiguration.getUploadDir()).toAbsolutePath().normalize();
    }

    public String storeFile(MultipartFile picture, Long productId, Long userId) {
        List<String> parts = Arrays.asList(Objects.requireNonNull(picture.getOriginalFilename()).split("[.]"));
        String extension = parts.get(parts.size() - 1);
        String filename = userId + "_" + productId + "_" + System.currentTimeMillis() + "." + extension;
        File file = new File(fileStorageConfiguration.getUploadDir()+"/"+ filename);
        try {
            picture.transferTo(file.toPath());
        } catch (IOException e) {

        }
        return "/image/"+filename;
    }
}
