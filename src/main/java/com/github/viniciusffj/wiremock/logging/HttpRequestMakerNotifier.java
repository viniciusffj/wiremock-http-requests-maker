package com.github.viniciusffj.wiremock.logging;

import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.viniciusffj.wiremock.http.HttpRequestParameters;

public class HttpRequestMakerNotifier {
    private Notifier notifier;

    public HttpRequestMakerNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public void hasParameters(HttpRequestParameters httpRequestParameters) {
        String message = String.format("Parameters were passed: %s", httpRequestParameters);
        this.notifier.info(formatMessage(message));
    }

    public void hasNoParameters() {
        this.notifier.info(formatMessage("No parameters were passed"));
    }

    private String formatMessage(String message) {
        return String.format("[http-request-maker]: %s", message);
    }
}
