package com.github.viniciusffj.wiremock.http;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpClientOPTIONSIntegrationTest {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8004);

    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        this.httpClient = new HttpClient();
    }

    @Test
    public void should_make_successful_request() throws Exception {
        stubFor(options(urlEqualTo("/my/resource"))
                .willReturn(aResponse()
                        .withStatus(200)));

        HttpClientResponse response = httpClient.execute("http://localhost:8004/my/resource", HttpMethod.OPTIONS);

        assertThat(response.hasError(), is(false));
        verify(optionsRequestedFor(urlMatching("/my/resource")));
    }

}