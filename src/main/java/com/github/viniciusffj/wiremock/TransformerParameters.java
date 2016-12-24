package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.extension.Parameters;

import java.util.Map;

public class TransformerParameters {
    private static final String ROOT_PARAMETER = "http_request_maker";
    private static final String URL_PARAMETER = "url";

    private Parameters parameters;

    public TransformerParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public boolean hasRequestMakerParameter() {
        return parameters != null && parameters.containsKey(ROOT_PARAMETER);
    }

    public String getUrl() {
        return (String) getParametersMap().get(URL_PARAMETER);
    }

    private Map getParametersMap() {
        return (Map) parameters.get(ROOT_PARAMETER);
    }
}
