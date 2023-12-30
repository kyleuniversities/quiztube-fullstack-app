package com.ku.quizzical.app.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3StorageConfiguration {
    // Instance Fields
    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.s3.bucketpath}")
    private String bucketPath;

    public String getBucketPath() {
        return this.bucketPath;
    }

    @Bean
    public S3Client s3Client() {
        if (this.bucketPath.contains("http")) {
            return S3Client.builder()
                    .region(Region.of(this.awsRegion))
                    // TODO (Later): Configure credentials later
                    // .credentialsProvider(AwsCredentialsProvider + AwsCredentials)
                    .build();
        }
        return new FakeS3Client(this.bucketPath);
    }
}
