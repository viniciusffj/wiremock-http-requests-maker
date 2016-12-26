package com.github.viniciusffj.wiremock.http;

import com.github.viniciusffj.wiremock.logging.HttpRequestMakerNotifier;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import org.apache.commons.lang3.StringUtils;

public class HttpClient {

    private HttpRequestMakerNotifier notifier;

    public HttpClient(HttpRequestMakerNotifier notifier) {
        this.notifier = notifier;
    }

    public HttpClientResponse execute(HttpRequestParameters httpRequestParameters) {
        if (httpRequestParameters == null) {
            throw new IllegalArgumentException("httpRequestParameters can't be null");
        }

        if (StringUtils.isEmpty(httpRequestParameters.getUrl())) {
            throw new IllegalArgumentException("Url can't be empty");
        }

        this.notifier.requestAttempt(httpRequestParameters);

        try {
            HttpResponse<String> response = createHttpRequest(httpRequestParameters, httpRequestParameters.getMethod())
                    .headers(httpRequestParameters.getHeaders())
                    .asString();

            return HttpClientResponse.success(response.getStatus(), response.getBody());
        } catch (Exception e) {
            return HttpClientResponse.error(e);
        }
    }

    private HttpRequest createHttpRequest(HttpRequestParameters parameters, HttpMethod httpMethod) {
        String url = parameters.getUrl();
        HttpRequest request = null;

        switch (httpMethod) {
            case GET:
                request = Unirest.get(url);
                break;
            case POST:
                request = Unirest.post(url)
                        .body(parameters.getBody())
                        .getHttpRequest();
                break;
            case PUT:
                request = Unirest.put(url)
                        .body(parameters.getBody())
                        .getHttpRequest();
                break;
            case PATCH:
                request = Unirest.patch(url)
                        .body(parameters.getBody())
                        .getHttpRequest();
                break;
            case DELETE:
                request = Unirest.delete(url)
                        .body(parameters.getBody())
                        .getHttpRequest();
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
