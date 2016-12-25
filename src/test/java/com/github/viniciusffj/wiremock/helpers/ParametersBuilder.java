package com.github.viniciusffj.wiremock.helpers;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.viniciusffj.wiremock.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class ParametersBuilder {

    private String url = "http://localhost:9000";
    private HttpMethod method = HttpMethod.GET;
    private Map<String, String> headers = new HashMap<>();

    public ParametersBuilder url(String url) {
        this.url = url;
        return this;
    }

    public ParametersBuilder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public ParametersBuilder header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public Parameters build() {
        Map<String, Object> map = buildHttpRequestMakerParameters();
        return Parameters.one("http_request_maker", map);
    }

    public Map<String, Object> buildHttpRequestMakerParameters() {
        return new HashMap<String, Object>() {{
            put("url", url);
            put("method", method.toString());
            if (!headers.isEmpty()) {
                put("headers", headers);
            }
        }};
    }

    public Parameters empty() {
        return Parameters.empty();
    }

}
