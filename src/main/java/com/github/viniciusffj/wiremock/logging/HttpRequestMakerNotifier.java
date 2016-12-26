package com.github.viniciusffj.wiremock.logging;

import com.github.tomakehurst.wiremock.common.Notifier;

public class HttpRequestMakerNotifier {
    private Notifier notifier;

    public HttpRequestMakerNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public void hasParameters() {
        this.notifier.info(formatMessage("Parameters were passed"));
    }

    public void hasNoParameters() {
        this.notifier.info(formatMessage("No parameters were passed"));
    }

    private String formatMessage(String message) {
        return String.format("[http-request-maker]: %s", message);
    }
}
