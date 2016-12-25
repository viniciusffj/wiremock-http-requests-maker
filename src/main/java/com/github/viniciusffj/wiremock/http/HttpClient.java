package com.github.viniciusffj.wiremock.http;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    public HttpClientResponse execute(String url, HttpMethod httpMethod) {
        return execute(url, httpMethod, new HashMap<String, String>());
    }

    public HttpClientResponse execute(String url, HttpMethod httpMethod, Map<String, String> headers) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("Url can't be empty");
        }

        if (httpMethod == null) {
            throw new IllegalArgumentException("HTTP method can't be null");
        }

        if (headers == null) {
            throw new IllegalArgumentException("Headers can't be null, one can send a new HashMap instead");
        }

        try {
            createHttpRequest(url, httpMethod)
                    .headers(headers)
                    .asString();
        } catch (Exception e) {
            return HttpClientResponse.error();
        }

        return HttpClientResponse.success();
    }

    private HttpRequest createHttpRequest(String url, HttpMethod httpMethod) {
        HttpRequest request = null;

        switch (httpMethod) {
            case GET:
                request = Unirest.get(url);
                break;
            case POST:
                request = Unirest.post(url);
                break;
            case PUT:
                request = Unirest.put(url);
                break;
            case PATCH:
                request = Unirest.patch(url);
                break;
            case DELETE:
                request = Unirest.delete(url);
                break;
            case HEAD:
                request = Unirest.head(url);
                break;
            case OPTIONS:
                request = Unirest.options(url);
                break;
        }

        return request;
    }
}
