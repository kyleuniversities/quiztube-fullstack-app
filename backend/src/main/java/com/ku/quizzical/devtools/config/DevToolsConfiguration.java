package com.ku.quizzical.devtools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevToolsConfiguration {
    @Value("${devtools.resources.path}")
    private String resourcesPath;

    public String getResourcesPath() {
        return this.resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }
}
