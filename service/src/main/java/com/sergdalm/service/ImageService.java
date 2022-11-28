package com.sergdalm.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Service;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@ConstructorBinding
public class ImageService {

    private final String bucket;

    public ImageService(@Value("${app.image.bucket}") String bucket) {
        this.bucket = bucket;
    }

    @SneakyThrows
    public void upload(String imagePath, java.io.InputStream content) {
        java.nio.file.Path fullImagePath = java.nio.file.Path.of(bucket, imagePath);

        try (content) {
            java.nio.file.Files.createDirectories(fullImagePath.getParent());
            java.nio.file.Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    // Если бы это был большой файл, лучше возвращать InputStream
    public java.util.Optional<byte[]> get(String imagePath) {
        java.nio.file.Path fullImagePath = java.nio.file.Path.of(bucket, imagePath);

        return java.nio.file.Files.exists(fullImagePath)
                ? java.util.Optional.of(java.nio.file.Files.readAllBytes(fullImagePath))
                : java.util.Optional.empty();
    }
}
