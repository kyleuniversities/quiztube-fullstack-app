package com.ku.quizzical.app.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class FakeS3Client implements S3Client {
    private String mockFolderPath;

    public FakeS3Client(String mockFolderPath) {
        super();
        this.mockFolderPath = mockFolderPath;
    }

    @Override
    public String serviceName() {
        return "fake-s3";
    }

    @Override
    public void close() {
        // Do Nothing
    }

    @Override
    public PutObjectResponse putObject(PutObjectRequest putObjectRequest, RequestBody requestBody)
            throws AwsServiceException, SdkClientException {
        InputStream inputStream = requestBody.contentStreamProvider().newStream();
        try {
            byte[] fileBytes = IOUtils.toByteArray(inputStream);
            FileUtils.writeByteArrayToFile(
                    new File(putObjectRequest.bucket(), putObjectRequest.key()), fileBytes);
            return PutObjectResponse.builder().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseInputStream<GetObjectResponse> getObject(GetObjectRequest getObjectRequest)
            throws AwsServiceException, SdkClientException {
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    collectObjectFullPath(getObjectRequest.bucket(), getObjectRequest.key()));
            return new ResponseInputStream<>(GetObjectResponse.builder().build(), fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String collectObjectFullPath(String bucketName, String key) {
        return this.mockFolderPath + "/" + key;
    }
}
