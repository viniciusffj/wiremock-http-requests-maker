package com.github.viniciusffj.wiremock.integration.http;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.viniciusffj.wiremock.http.HttpClient;
import com.github.viniciusffj.wiremock.http.HttpClientResponse;
import com.github.viniciusffj.wiremock.http.HttpMethod;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.HashMap;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpClientGETIntegrationTest {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8002);

    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        this.httpClient = new HttpClient();
    }

    @Test
    public void should_make_successful_request() throws Exception {
        stubFor(get(urlEqualTo("/my/resource"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)));

        HashMap<String, String> headers = new HashMap<String, String>() {{
            put("Content-Type", "application/json");
        }};

        HttpClientResponse response = httpClient.execute("http://localhost:8002/my/resource", HttpMethod.GET, headers);

        assertThat(response.hasError(), is(false));
        verify(getRequestedFor(urlMatching("/my/resource"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    public void should_have_error_response_when_making_a_get_to_unknown_host() throws Exception {
        HttpClientResponse httpClientResponse = this.httpClient.execute("http://this.host.doesnt.exist.com/service", HttpMethod.GET);

        assertThat(httpClientResponse.hasError(), is(true));
    }
}