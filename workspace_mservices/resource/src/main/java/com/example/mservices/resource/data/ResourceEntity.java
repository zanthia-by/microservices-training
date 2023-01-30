package com.example.mservices.resource.data;

import jakarta.persistence.*;

@Entity
@Table(name = "RESOURCE")
public class ResourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_generator")
    @SequenceGenerator(name="resource_generator", sequenceName = "RESOURCE_SEQ", allocationSize=1)
    @Column(name = "ID")
    private long id;

    @Column(name = "S3_BUCKET_NAME")
    private String bucketName;

    @Column(name = "S3_OBJECT_KEY")
    private String objectKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    @Override
    public String toString() {
        return "ResourceEntity{" +
                "id=" + id +
                ", bucketName='" + bucketName + '\'' +
                ", objectKey='" + objectKey + '\'' +
                '}';
    }
}
