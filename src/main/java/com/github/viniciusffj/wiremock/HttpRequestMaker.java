package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.viniciusffj.wiremock.http.HttpClient;

public class HttpRequestMaker extends ResponseDefinitionTransformer {

    private HttpClient httpClient;

    /* Used by wiremock */
    public HttpRequestMaker() {
        this(new HttpClient());
    }

    protected HttpRequestMaker(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        TransformerParameters transformerParameters = new TransformerParameters(parameters);

        if (transformerParameters.hasRequestMakerParameter()) {
            httpClient.execute(transformerParameters.getUrl(), transformerParameters.getMethod(), transformerParameters.getHeaders());
        }

        return ResponseDefinitionBuilder.like(responseDefinition).build();
    }

    @Override
    public String getName() {
        return "http-request-maker";
    }
}
