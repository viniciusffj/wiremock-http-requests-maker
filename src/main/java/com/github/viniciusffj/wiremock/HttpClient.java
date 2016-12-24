package com.github.viniciusffj.wiremock;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HttpClient {
    public HttpClientResponse get(String url) {
        try {
            Unirest.get(url).asString();
        } catch (UnirestException e) {
            return HttpClientResponse.error();
        }
        return HttpClientResponse.success();
    }
}
