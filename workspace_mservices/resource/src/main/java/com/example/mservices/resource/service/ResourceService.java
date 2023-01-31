package com.example.mservices.resource.service;

import com.amazonaws.services.s3.model.S3Object;
import com.example.mservices.resource.data.ResourceEntity;
import com.example.mservices.resource.data.ResourceRepository;
import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.tika.config.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ResourceService {
    private final AmazonS3Service amazonS3Service;
    private final ResourceRepository resourceRepository;

    public ResourceService(AmazonS3Service amazonS3Service, ResourceRepository resourceRepository) {
        this.amazonS3Service = amazonS3Service;
        this.resourceRepository = resourceRepository;
    }

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public Long upload(InputStream inputStream) throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        MediaType mimetype = validate(bytes);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", mimetype.toString());
        metadata.put("Content-Length", String.valueOf(bytes.length));

        String objectKey = UUID.randomUUID().toString();
        amazonS3Service.upload(bucketName, objectKey, metadata, new ByteArrayInputStream(bytes));
        var newResource = resourceRepository.save(createResource(bucketName, objectKey));
        return newResource.getId();
    }

    private MediaType validate(byte[] bytes) {
        try {
            TikaConfig tika = new TikaConfig();
            Metadata metadata = new Metadata();
            MediaType mimetype = tika.getDetector().detect(
                    TikaInputStream.get(new ByteArrayInputStream(bytes)), metadata);
            if (!"audio/mpeg".equals(mimetype.toString())) {
                throw new InvalidAudioMpegException();
            }
            return mimetype;
        } catch (TikaException | IOException e) {
            throw new InvalidAudioMpegException(e);
        }
    }

    private ResourceEntity createResource(String bucketName, String objectKey) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setBucketName(bucketName);
        resourceEntity.setObjectKey(objectKey);
        return resourceEntity;
    }

    public S3Object download(long id) {
        ResourceEntity resourceEntity = resourceRepository.findById(id).orElse(null);
        if (resourceEntity == null) {
            return null;
        }
        return amazonS3Service.download(resourceEntity.getBucketName(), resourceEntity.getObjectKey());
    }

    public List<Long> delete(List<Long> ids) {
        List<Long> deletedIds = new ArrayList<>();
        for (long id : ids) {
            var resourceEntity = resourceRepository.findById(id).orElse(null);
            if (resourceEntity == null) {
                continue;
            }
            deletedIds.add(resourceEntity.getId());
            amazonS3Service.delete(resourceEntity.getBucketName(), resourceEntity.getObjectKey());
            resourceRepository.delete(resourceEntity);
        }
        deletedIds.sort(Comparator.naturalOrder());
        return deletedIds;
    }
}
