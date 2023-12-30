package com.ku.quizzical.app.s3;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.string.StringHelper;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3StorageService {
    // Instance Fields
    private final S3StorageConfiguration configuration;
    private final S3Client s3Client;

    // Constructor
    public S3StorageService(S3StorageConfiguration configuration, S3Client s3Client) {
        this.configuration = configuration;
        this.s3Client = s3Client;
    }

    // Main Instance Methods
    public void putObject(String key, byte[] file) {
        String bucketName = this.collectBucketName();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(key).build();
        this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
    }

    public byte[] getObject(String key) {
        String bucketName = this.collectBucketName();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();
        ResponseInputStream<GetObjectResponse> response = this.s3Client.getObject(getObjectRequest);
        try {
            return response.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String collectBucketName() {
        String bucketPath = this.configuration.getBucketPath();
        if (bucketPath.contains("http")) {
            int doubleSlashIndex = StringHelper.indexOf(bucketPath, "//");
            int s3Index = StringHelper.indexOf(bucketPath, ".s3");
            return StringHelper.substring(bucketPath, doubleSlashIndex + 2, s3Index);
        }
        return "null";
    }
}
