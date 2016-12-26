package com.github.viniciusffj.wiremock.logging;

import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.viniciusffj.wiremock.http.HttpClientResponse;
import com.github.viniciusffj.wiremock.http.HttpRequestParameters;

public class HttpRequestMakerNotifier {
    private Notifier notifier;

    public HttpRequestMakerNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public void hasNoParameters() {
        this.notifier.info(formatMessage("No parameters were passed"));
    }

    private String formatMessage(String message) {
        return String.format("[http-request-maker]: %s", message);
    }

    public void requestAttempt(HttpRequestParameters httpRequestParameters) {
        String message = String.format("Request attempt: %s", httpRequestParameters);
        this.notifier.info(formatMessage(message));
    }

    public void successfulHttpResponse(HttpClientResponse response) {
        String message = String.format("Http response: {status=%s, body=%s}", response.getStatus(), response.getBody());
        this.notifier.info(formatMessage(message));
    }

    public void errorHttpResponse(HttpClientResponse response) {
        this.notifier.error(formatMessage("Failed http response"), response.getError());
    }
}
