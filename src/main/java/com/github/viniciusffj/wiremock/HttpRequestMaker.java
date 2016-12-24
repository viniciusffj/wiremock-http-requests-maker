package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Map;

public class HttpRequestMaker extends ResponseDefinitionTransformer {

    @Override
    public boolean applyGlobally() {
        return false;
    }

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
        Map http_request_maker = (Map) parameters.get("http_request_maker");
        try {
            new HttpClient().get((String) http_request_maker.get("url"));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return ResponseDefinitionBuilder.like(responseDefinition).build();
    }

    @Override
    public String getName() {
        return "http-request-maker";
    }
}
