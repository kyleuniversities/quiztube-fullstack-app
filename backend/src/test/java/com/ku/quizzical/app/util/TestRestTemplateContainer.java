package com.ku.quizzical.app.util;

import java.util.List;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import com.ku.quizzical.common.util.string.StringFunction;

/**
 * Wrapping class for localizing the Test Rest Template
 */
public final class TestRestTemplateContainer {
    // Instance Fields
    private TestRestTemplate restTemplate;
    private StringFunction toFullUrl;

    // New Instance Method
    public static TestRestTemplateContainer newInstance(TestRestTemplate restTemplate,
            StringFunction toFullUrl) {
        return new TestRestTemplateContainer(restTemplate, toFullUrl);
    }

    // Constructor Method
    private TestRestTemplateContainer(TestRestTemplate restTemplate, StringFunction toFullUrl) {
        super();
        this.restTemplate = restTemplate;
        this.toFullUrl = toFullUrl;
    }

    // Accessor Methods
    public TestRestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    public StringFunction getToFullUrl() {
        return this.toFullUrl;
    }

    // Main Instance Methods
    public <T> T post(String url, HttpEntity<String> request, Class<T> responseType) {
        return this.restTemplate.postForEntity(this.toFullUrl.apply(url), request, responseType)
                .getBody();
    }

    public <T> List<T> getList(String url) {
        return (List<T>) this.restTemplate.getForObject(this.toFullUrl.apply(url), List.class);
    }

    public <T> T getObject(String url, Class<T> responseType) {
        return this.restTemplate.getForObject(this.toFullUrl.apply(url), responseType);
    }

    public <T> T patch(String url, HttpEntity<String> request, Class<T> responseType) {
        return this.restTemplate.patchForObject(this.toFullUrl.apply(url), request, responseType);
    }

    public void delete(String url) {
        this.restTemplate.delete(this.toFullUrl.apply(url));
    }
}
