package com.motollantas.MotoLlantasVirtual.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    String uploadFile(MultipartFile file) throws IOException;
}
