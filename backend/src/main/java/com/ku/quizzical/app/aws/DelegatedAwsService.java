package com.ku.quizzical.app.aws;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

/**
 * Service class for AWS related operations
 */
@Service
public class DelegatedAwsService implements AwsCredentialsProvider {
    private DelegatedAwsConfiguration configuration;

    public DelegatedAwsService(DelegatedAwsConfiguration configuration) {
        super();
        this.configuration = configuration;
    }

    @Override
    public AwsCredentials resolveCredentials() {
        return DelegatedAwsCredentials.newInstance(this.configuration.getAccessKeyId(),
                this.configuration.getSecretAccessKey());
    }
}
