package com.github.viniciusffj.wiremock.http;

import com.github.tomakehurst.wiremock.extension.Parameters;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestParameters {
    private static final String ROOT_PARAMETER = "http_request_maker";
    private static final String URL_PARAMETER = "url";
    private static final String METHOD_PARAMETER = "method";
    private static final String HEADER_PARAMETER = "headers";
    private static final String BODY_PARAMETER = "body";

    private Parameters parameters;

    public HttpRequestParameters(Parameters parameters) {
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
        if (hasParameter(HEADER_PARAMETER)) {
            return (Map<String, String>) getParameter(HEADER_PARAMETER);
        }

        return new HashMap<>();
    }

    private boolean hasParameter(String propertyName) {
        return getHttpRequestMakerMap().containsKey(propertyName);
    }

    public String getBody() {
        if (hasParameter(BODY_PARAMETER)) {
            return getStringParameter(BODY_PARAMETER);
        }

        return "";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("url", this.getUrl())
                .append("method", this.getMethod())
                .append("body", this.getBody())
                .append("headers", this.headersToString(this.getHeaders()))
                .build();
    }

    private String headersToString(Map<String, String> map) {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);

        for (String key: map.keySet()) {
            toStringBuilder.append(key, map.get(key));
        }

        return toStringBuilder.build();
    }

}
