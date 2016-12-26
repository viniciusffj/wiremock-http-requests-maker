package com.github.viniciusffj.wiremock.http;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.viniciusffj.wiremock.helpers.HttpRequestParametersBuilder;
import com.github.viniciusffj.wiremock.logging.HttpRequestMakerNotifier;
import org.junit.Before;
import org.junit.Test;

public class HttpClientTest {

    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        httpClient = new HttpClient(new HttpRequestMakerNotifier(new ConsoleNotifier(true)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_invalid_argument_when_url_is_null() throws Exception {
        HttpRequestParameters httpRequestParameters = new HttpRequestParametersBuilder()
                .url(null)
                .method(HttpMethod.GET)
                .build();

        httpClient.execute(httpRequestParameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_invalid_argument_when_url_is_empty() throws Exception {
        HttpRequestParameters httpRequestParameters = new HttpRequestParametersBuilder()
                .url("")
                .method(HttpMethod.GET)
                .build();

        httpClient.execute(httpRequestParameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_invalid_argument_when_parameters_is_null() throws Exception {
        httpClient.execute(null);
    }

}