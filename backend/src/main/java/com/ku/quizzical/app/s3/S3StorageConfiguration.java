package com.ku.quizzical.app.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ku.quizzical.app.aws.DelegatedAwsService;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3StorageConfiguration {
    // Instance Fields
    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.s3.bucket-path}")
    private String bucketPath;

    private DelegatedAwsService awsService;

    public String getBucketPath() {
        return this.bucketPath;
    }

    public S3StorageConfiguration(DelegatedAwsService awsService) {
        this.awsService = awsService;
    }

    @Bean
    public S3Client s3Client() {
        if (this.bucketPath.contains("http")) {
            return S3Client.builder().region(Region.of(this.awsRegion))
                    .credentialsProvider(this.awsService).build();
        }
        return new FakeS3Client(this.bucketPath);
    }
}
