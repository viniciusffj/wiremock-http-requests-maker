package com.github.viniciusffj.wiremock.helpers;

import com.github.viniciusffj.wiremock.http.HttpMethod;
import com.github.viniciusffj.wiremock.http.HttpRequestParameters;

public class HttpRequestParametersBuilder {

    private ParametersBuilder parametersBuilder = new ParametersBuilder();

    public HttpRequestParametersBuilder url(String url) {
        this.parametersBuilder.url(url);
        return this;
    }

    public HttpRequestParametersBuilder method(HttpMethod method) {
        this.parametersBuilder.method(method);
        return this;
    }

    public HttpRequestParametersBuilder header(String key, String value) {
        this.parametersBuilder.header(key, value);
        return this;
    }

    public HttpRequestParametersBuilder body(String body) {
        this.parametersBuilder.body(body);
        return this;
    }

    public HttpRequestParameters build() {
        return new HttpRequestParameters(this.parametersBuilder.build());
    }
}
