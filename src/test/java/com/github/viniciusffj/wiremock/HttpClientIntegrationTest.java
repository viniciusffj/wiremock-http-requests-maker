package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpClientIntegrationTest {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8001);

    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        this.httpClient = new HttpClient();
    }

    @Test
    public void should_make_successful_get_request() throws Exception {
        stubFor(get(urlEqualTo("/my/resource"))
                .willReturn(aResponse()
                        .withStatus(200)));

        HttpClientResponse response = httpClient.get("http://localhost:8001/my/resource");

        assertThat(response.hasError(), is(false));
        verify(getRequestedFor(urlMatching("/my/resource")));
    }

    @Test
    public void should_have_error_response_when_host_is_wrong() throws Exception {
        HttpClientResponse httpClientResponse = this.httpClient.get("http://this.host.doesnt.exist.com/service");

        assertThat(httpClientResponse.hasError(), is(true));
    }
}