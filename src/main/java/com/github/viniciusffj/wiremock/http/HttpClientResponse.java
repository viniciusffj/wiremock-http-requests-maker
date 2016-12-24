package com.github.viniciusffj.wiremock.http;

public class HttpClientResponse {
    private boolean hasError;

    public HttpClientResponse(boolean hasError) {
        this.hasError = hasError;
    }

    public boolean hasError() {
        return hasError;
    }

    public static HttpClientResponse success() {
        return new HttpClientResponse(false);
    }

    public static HttpClientResponse error() {
        return new HttpClientResponse(true);
    }
}
