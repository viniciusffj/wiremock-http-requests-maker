package com.github.viniciusffj.wiremock.http;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

public class HttpClient {

    public HttpClientResponse execute(String url, HttpMethod httpMethod) {
        try {
            HttpRequest request;
            request = createHttpRequest(url, httpMethod);

            request.asString();
        } catch (UnirestException e) {
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
