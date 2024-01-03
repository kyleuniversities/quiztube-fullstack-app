package com.ku.quizzical.app.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for S3 Buckets
 */
@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets")
public class S3StorageBucketsModel {
    // Instance Fields
    private String master;

    // Accessor Methods
    public String getMaster() {
        return this.master;
    }

    // Mutator Methods
    public void setMaster(String master) {
        this.master = master;
    }
}
