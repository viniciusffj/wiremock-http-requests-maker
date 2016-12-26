package com.github.viniciusffj.wiremock.http;

public class HttpClientResponse {
    private boolean hasError;
    private Throwable error;
    private int status;
    private String body;

    private HttpClientResponse(boolean hasError) {
        this.hasError = hasError;
    }

    private HttpClientResponse(Throwable error) {
        this(true);
        this.error = error;
    }

    public HttpClientResponse(int status, String body) {
        this(false);
        this.status = status;
        this.body = body;
    }

    public boolean hasError() {
        return hasError;
    }

    public static HttpClientResponse success(int status, String body) {
        return new HttpClientResponse(status, body);
    }

    public static HttpClientResponse error(Throwable error) {
        return new HttpClientResponse(error);
    }

    public Throwable getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}
