package com.github.viniciusffj.wiremock.http;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpClientPOSTIntegrationTest {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8002);

    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        this.httpClient = new HttpClient();
    }

    @Test
    public void should_make_succesful_post_request() throws Exception {
        stubFor(post(urlEqualTo("/my/resource"))
                .willReturn(aResponse()
                        .withStatus(200)));

        HttpClientResponse response = httpClient.execute("http://localhost:8002/my/resource", HttpMethod.POST);

        assertThat(response.hasError(), is(false));
        verify(postRequestedFor(urlMatching("/my/resource")));
    }

    @Test
    public void should_have_error_response_when_making_a_post_to_unknown_host() throws Exception {
        HttpClientResponse httpClientResponse = this.httpClient.execute("http://this.host.doesnt.exist.com/service", HttpMethod.POST);

        assertThat(httpClientResponse.hasError(), is(true));
    }
}