package com.example.mservices.resource.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class AmazonS3Service {
    private final AmazonS3 amazonS3;

    public AmazonS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void upload(String bucketName, String key, Map<String, String> metadata, InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        metadata.forEach(objectMetadata::addUserMetadata);
        amazonS3.putObject(bucketName, key, inputStream, objectMetadata);
    }

    public S3Object download(String bucketName, String key) {
        return amazonS3.getObject(bucketName, key);
    }

    public void delete(String bucketName, String key) {
        amazonS3.deleteObject(bucketName, key);
    }
}
