package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import com.motollantas.MotoLlantasVirtual.Service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {
    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);

        try (S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build()) {

            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes())
            );

            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
        }
    }

}
