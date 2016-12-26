package com.github.viniciusffj.wiremock.helpers;

import com.github.viniciusffj.wiremock.http.HttpClientResponse;

public class HttpResponseBuilder {
    public HttpClientResponse success() {
        return HttpClientResponse.success(200, "{\"id\":\"123\"}");
    }
}
