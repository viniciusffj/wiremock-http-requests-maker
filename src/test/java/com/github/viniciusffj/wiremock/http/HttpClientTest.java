package com.github.viniciusffj.wiremock.http;

import org.junit.Before;
import org.junit.Test;

public class HttpClientTest {

    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        httpClient = new HttpClient();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_invalid_argument_when_url_is_null() throws Exception {
        httpClient.execute(null, HttpMethod.GET);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_invalid_argument_when_url_is_empty() throws Exception {
        httpClient.execute("", HttpMethod.GET);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_invalid_argument_when_method_is_null() throws Exception {
        httpClient.execute("http://localhost", null);
    }
}