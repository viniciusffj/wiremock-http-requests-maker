package com.github.viniciusffj.wiremock.http;

import org.apache.commons.lang3.StringUtils;

public enum  HttpMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE,
    HEAD,
    OPTIONS;

    public static HttpMethod fromString(String method) {
        String methodName = StringUtils.isEmpty(method) ? "" : StringUtils.upperCase(method);
        try {
            return valueOf(methodName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Invalid http method: %s", methodName));
        }
    }
}
