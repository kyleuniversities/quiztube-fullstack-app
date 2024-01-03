package com.ku.quizzical.devtools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Dev Tools related operations
 */
@Configuration
public class DevToolsConfiguration {
    @Value("${devtools.project.path}")
    private String projectPath;

    public String getProjectPath() {
        return this.projectPath;
    }

    public String getResourcesPath() {
        return this.projectPath + "/backend/src/main/resources";
    }

    public String getFrontendSourcePath() {
        return this.projectPath + "/frontend/src";
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
