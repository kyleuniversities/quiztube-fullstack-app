package com.ku.quizzical.app.aws;

import software.amazon.awssdk.auth.credentials.AwsCredentials;

public final class DelegatedAwsCredentials implements AwsCredentials {
    // Instance Fields
    private String accessKeyId;
    private String secretAccessKey;

    // New Instance Method
    public static DelegatedAwsCredentials newInstance(String accessKeyId, String secretAccessKey) {
        return new DelegatedAwsCredentials(accessKeyId, secretAccessKey);
    }

    // Constructor Method
    private DelegatedAwsCredentials(String accessKeyId, String secretAccessKey) {
        super();
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
    }

    // Accessor Methods
    @Override
    public String accessKeyId() {
        return this.accessKeyId;
    }

    @Override
    public String secretAccessKey() {
        return this.secretAccessKey;
    }
}
