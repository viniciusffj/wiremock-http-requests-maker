package com.github.viniciusffj.wiremock.http;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import org.apache.commons.lang3.StringUtils;

public class HttpClient {

    public HttpClientResponse execute(String url, HttpMethod httpMethod) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("Url can't be empty");
        }

        if (httpMethod == null) {
            throw new IllegalArgumentException("HTTP method can't be null");
        }

        try {
            HttpRequest request;
            request = createHttpRequest(url, httpMethod);

            request.asString();
        } catch (Exception e) {
            return HttpClientResponse.error();
        }

        return HttpClientResponse.success();
    }

    private HttpRequest createHttpRequest(String url, HttpMethod httpMethod) {
        HttpRequest request;

        switch (httpMethod) {
            case POST:
                request = Unirest.post(url);
                break;
            default:
                request = Unirest.get(url);
                break;
        }

        return request;
    }
}
