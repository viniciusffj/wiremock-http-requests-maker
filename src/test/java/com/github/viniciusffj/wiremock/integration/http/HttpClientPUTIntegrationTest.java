package com.github.viniciusffj.wiremock.integration.http;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.viniciusffj.wiremock.http.HttpClient;
import com.github.viniciusffj.wiremock.http.HttpClientResponse;
import com.github.viniciusffj.wiremock.http.HttpMethod;
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
        this.httpClient = new HttpClient();
    }

    @Test
    public void should_make_successful_request() throws Exception {
        stubFor(put(urlEqualTo("/my/resource"))
                .willReturn(aResponse()
                        .withStatus(200)));

        HttpClientResponse response = httpClient.execute("http://localhost:8007/my/resource", HttpMethod.PUT);

        assertThat(response.hasError(), is(false));
        verify(putRequestedFor(urlMatching("/my/resource")));
    }

}