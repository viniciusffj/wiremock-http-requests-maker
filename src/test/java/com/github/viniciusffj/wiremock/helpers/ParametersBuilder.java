package com.github.viniciusffj.wiremock.helpers;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.viniciusffj.wiremock.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class ParametersBuilder {

    private String url = "http://localhost:9000";;
    private HttpMethod method = HttpMethod.GET;

    public ParametersBuilder url(String url) {
        this.url = url;
        return this;
    }

    public ParametersBuilder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public Parameters build() {
        Map<String, String> map = new HashMap<String, String>() {{
            put("url", url);
            put("method", method.toString());
        }};

        return Parameters.one("http_request_maker", map);
    }

    public Map<String, String> buildHttpRequestMakerParameters() {
        return new HashMap<String, String>() {{
            put("url", url);
            put("method", method.toString());
        }};
    }

    public Parameters empty() {
        return Parameters.empty();
    }

}
