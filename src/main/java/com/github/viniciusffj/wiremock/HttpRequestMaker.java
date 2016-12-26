package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.viniciusffj.wiremock.http.HttpClient;
import com.github.viniciusffj.wiremock.http.HttpRequestParameters;
import com.github.viniciusffj.wiremock.logging.HttpRequestMakerNotifier;

public class HttpRequestMaker extends ResponseDefinitionTransformer {

    private HttpClient httpClient;
    private HttpRequestMakerNotifier notifier;

    /* Used by wiremock */
    public HttpRequestMaker() {
        this(new HttpClient(new HttpRequestMakerNotifier(new ConsoleNotifier(true))), new HttpRequestMakerNotifier(new ConsoleNotifier(true)));
    }

    protected HttpRequestMaker(HttpClient httpClient, HttpRequestMakerNotifier notifier) {
        this.httpClient = httpClient;
        this.notifier = notifier;
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(parameters);

        if (httpRequestParameters.hasRequestMakerParameter()) {
            this.notifier.hasParameters(httpRequestParameters);
            httpClient.execute(httpRequestParameters);
        } else {
            this.notifier.hasNoParameters();
        }

        return ResponseDefinitionBuilder.like(responseDefinition).build();
    }

    @Override
    public String getName() {
        return "http-request-maker";
    }
}
