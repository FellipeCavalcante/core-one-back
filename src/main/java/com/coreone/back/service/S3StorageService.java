package com.coreone.back.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3StorageService {

    private final S3Client s3Client;
    private final String bucket;

    public S3StorageService(S3Client s3Client,
                            @Value("${aws.s3.bucket}") String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    public String uploadFile(MultipartFile file, String folderPath) {
        try {
            String key = folderPath != null && !folderPath.isEmpty()
                    ? folderPath + "/" + file.getOriginalFilename()
                    : file.getOriginalFilename();

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

            return key;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo", e);
        } catch (S3Exception e) {
            throw new RuntimeException("Erro ao enviar para S3: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    public byte[] downloadFile(String key) {
        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            return s3Client.getObjectAsBytes(getRequest).asByteArray();
        } catch (S3Exception e) {
            throw new RuntimeException("Erro ao baixar arquivo: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    public void deleteFile(String key) {
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteRequest);
        } catch (S3Exception e) {
            throw new RuntimeException("Erro ao deletar arquivo: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    public List<String> listFiles(String folderPath) {
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(bucket)
                    .prefix(folderPath)
                    .build();

            ListObjectsV2Response response = s3Client.listObjectsV2(listRequest);

            return response.contents().stream()
                    .map(S3Object::key)
                    .collect(Collectors.toList());
        } catch (S3Exception e) {
            throw new RuntimeException("Erro ao listar arquivos: " + e.awsErrorDetails().errorMessage(), e);
        }
    }
}
