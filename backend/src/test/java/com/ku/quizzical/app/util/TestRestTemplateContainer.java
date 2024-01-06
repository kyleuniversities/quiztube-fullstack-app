package com.ku.quizzical.app.util;

import java.util.List;
import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import com.ku.quizzical.app.helper.HttpHelper;
import com.ku.quizzical.app.helper.JsonHelper;
import com.ku.quizzical.common.util.string.StringFunction;

/**
 * Wrapping class for localizing the Test Rest Template
 */
public final class TestRestTemplateContainer {
    // Instance Fields
    private TestRestTemplate restTemplate;
    private StringFunction toFullUrl;
    private HttpHeaders headers;

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
        this.headers = HttpHelper.newGeneralHttpHeaders();
    }

    // Accessor Methods
    public TestRestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    public StringFunction getToFullUrl() {
        return this.toFullUrl;
    }

    // Operant Methods
    public void setBearerToken(String token) {
        this.headers.setBearerAuth(token);
    }

    public void resetHeaders() {
        this.headers = HttpHelper.newGeneralHttpHeaders();
    }

    // Main Instance Methods
    public <T> T post(String url, JSONObject body, Class<T> responseType) {
        return this.restTemplate.exchange(this.toFullUrl.apply(url), HttpMethod.POST,
                HttpHelper.newHttpEntity(body, this.headers), responseType).getBody();
    }

    public <T> List<T> getList(String url) {
        return (List<T>) this.restTemplate.exchange(this.toFullUrl.apply(url), HttpMethod.GET,
                HttpHelper.newHttpEntity(JsonHelper.newJsonObject(), this.headers), List.class)
                .getBody();
    }

    public <T> T getObject(String url, Class<T> responseType) {
        return this.restTemplate.exchange(this.toFullUrl.apply(url), HttpMethod.GET,
                HttpHelper.newHttpEntity(JsonHelper.newJsonObject(), this.headers), responseType)
                .getBody();
    }

    public <T> T patch(String url, JSONObject body, Class<T> responseType) {
        return this.restTemplate.exchange(this.toFullUrl.apply(url), HttpMethod.PATCH,
                HttpHelper.newHttpEntity(body, this.headers), responseType).getBody();
    }

    public void delete(String url) {
        this.restTemplate.exchange(this.toFullUrl.apply(url), HttpMethod.DELETE,
                HttpHelper.newHttpEntity(JsonHelper.newJsonObject(), this.headers), String.class);
    }
}
