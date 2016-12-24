package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class HttpClientTest {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8001);

    @Test
    public void should_make_get_request() throws Exception {
        stubFor(get(urlEqualTo("/my/resource"))
                .willReturn(aResponse()
                        .withStatus(200)));


        HttpClient httpClient = new HttpClient();

        httpClient.get("http://localhost:8001/my/resource");

        verify(getRequestedFor(urlMatching("/my/resource")));
    }
}