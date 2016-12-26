package com.github.viniciusffj.wiremock.integration.http;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.viniciusffj.wiremock.helpers.HttpRequestParametersBuilder;
import com.github.viniciusffj.wiremock.http.HttpClient;
import com.github.viniciusffj.wiremock.http.HttpClientResponse;
import com.github.viniciusffj.wiremock.http.HttpMethod;
import com.github.viniciusffj.wiremock.http.HttpRequestParameters;
import com.github.viniciusffj.wiremock.logging.HttpRequestMakerNotifier;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpClientPUTIntegrationTest {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8007);

    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        this.httpClient = new HttpClient(new HttpRequestMakerNotifier(new ConsoleNotifier(true)));
    }

    @Test
    public void should_make_successful_request() throws Exception {
        stubFor(put(urlEqualTo("/my/resource"))
                .withRequestBody(equalTo("param=value"))
                .willReturn(aResponse()
                        .withStatus(200)));

        HttpRequestParameters httpRequestParameters = new HttpRequestParametersBuilder()
                .url("http://localhost:8007/my/resource")
                .body("param=value")
                .method(HttpMethod.PUT)
                .build();

        HttpClientResponse response = httpClient.execute(httpRequestParameters);

        assertThat(response.hasError(), is(false));
        verify(putRequestedFor(urlMatching("/my/resource"))
            .withRequestBody(equalTo("param=value")));
    }

}