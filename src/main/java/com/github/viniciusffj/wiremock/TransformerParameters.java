package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.viniciusffj.wiremock.http.HttpMethod;

import java.util.Map;

public class TransformerParameters {
    private static final String ROOT_PARAMETER = "http_request_maker";
    private static final String URL_PARAMETER = "url";
    private static final String METHOD_PARAMETER = "method";

    private Parameters parameters;

    public TransformerParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public boolean hasRequestMakerParameter() {
        return parameters != null && parameters.containsKey(ROOT_PARAMETER);
    }

    public String getUrl() {
        return getStringParameter(URL_PARAMETER);
    }

    private String getStringParameter(String urlParameter) {
        Map httpRequestMakerParameters = (Map) parameters.get(ROOT_PARAMETER);
        return (String) httpRequestMakerParameters.get(urlParameter);
    }

    public HttpMethod getMethod() {
        String method = getStringParameter(METHOD_PARAMETER);
        return HttpMethod.fromString(method);
    }
}
