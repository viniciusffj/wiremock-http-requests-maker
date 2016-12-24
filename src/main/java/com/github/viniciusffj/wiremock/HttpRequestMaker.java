package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HttpRequestMaker extends ResponseDefinitionTransformer {

    @Override
    public boolean applyGlobally() {
        return false;
    }

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        TransformerParameters transformerParameters = new TransformerParameters(parameters);

        if (transformerParameters.hasRequestMakerParameter()) {
            try {
                new HttpClient().get(transformerParameters.getUrl());
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }

        return ResponseDefinitionBuilder.like(responseDefinition).build();
    }

    @Override
    public String getName() {
        return "http-request-maker";
    }
}
