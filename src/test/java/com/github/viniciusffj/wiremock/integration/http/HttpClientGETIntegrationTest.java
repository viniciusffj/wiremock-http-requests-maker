package com.github.viniciusffj.wiremock.integration.http;

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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
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
                        .withBody("{}")
                        .withStatus(200)));

        HttpRequestParameters httpRequestParameters = new HttpRequestParametersBuilder()
                .url("http://localhost:8002/my/resource")
                .method(HttpMethod.GET)
                .header("Content-Type", "application/json")
                .build();

        HttpClientResponse response = httpClient.execute(httpRequestParameters);

        assertThat(response.hasError(), is(false));
        assertThat(response.getStatus(), is(200));
        assertThat(response.getBody(), is("{}"));

        verify(getRequestedFor(urlMatching("/my/resource"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    public void should_have_error_response_when_making_a_get_to_unknown_host() throws Exception {
        HttpRequestParameters httpRequestParameters =
                new HttpRequestParametersBuilder()
                        .url("http://not.existing.host.com/error")
                        .method(HttpMethod.GET)
                        .build();
        HttpClientResponse httpClientResponse = this.httpClient.execute(httpRequestParameters);

        assertThat(httpClientResponse.hasError(), is(true));
        assertThat(httpClientResponse.getError(), is(notNullValue()));
    }
}