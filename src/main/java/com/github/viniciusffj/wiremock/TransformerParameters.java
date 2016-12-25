package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.viniciusffj.wiremock.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class TransformerParameters {
    private static final String ROOT_PARAMETER = "http_request_maker";
    private static final String URL_PARAMETER = "url";
    private static final String METHOD_PARAMETER = "method";
    private static final String HEADER_PARAMETER = "headers";

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

    private String getStringParameter(String parameter) {
        return (String) getParameter(parameter);
    }

    private Object getParameter(String parameter) {
        return getHttpRequestMakerMap().get(parameter);
    }

    private Map getHttpRequestMakerMap() {
        return (Map) parameters.get(ROOT_PARAMETER);
    }

    public HttpMethod getMethod() {
        String method = getStringParameter(METHOD_PARAMETER);
        return HttpMethod.fromString(method);
    }

    public Map<String, String> getHeaders() {
        if (hasProperty(HEADER_PARAMETER)) {
            return (Map<String, String>) getParameter(HEADER_PARAMETER);
        }

        return new HashMap<>();
    }

    private boolean hasProperty(String propertyName) {
        return getHttpRequestMakerMap().containsKey(propertyName);
    }
}
