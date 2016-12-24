package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.google.common.collect.ImmutableMap;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class HttpRequestMakerIntegrationTest {

    @ClassRule
    public static WireMockClassRule serviceToBeInvoked = new WireMockClassRule(options().port(8001));

    @ClassRule
    public static WireMockClassRule serviceWithRequestMaker =
            new WireMockClassRule(options()
                    .port(8002)
                    .extensions("com.github.viniciusffj.wiremock.HttpRequestMaker"));

    private HttpRequestMaker httpRequestMaker;

    @Before
    public void setUp() throws Exception {
        this.httpRequestMaker = new HttpRequestMaker();

        serviceToBeInvoked.
                stubFor(get(urlEqualTo("/resource"))
                        .willReturn(aResponse()
                                .withStatus(200)));


        serviceWithRequestMaker
                .stubFor(get(urlEqualTo("/http-request-maker-transformer"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withTransformerParameter("http_request_maker", ImmutableMap.of("url", "http://localhost:8001/resource"))
                                .withTransformers("http-request-maker")));
    }

    @Test
    public void should_make_get_request_other_service() throws Exception {
        RestAssured
                .get("http://localhost:8002/http-request-maker-transformer")
                .then()
                .assertThat()
                .statusCode(200);

        serviceToBeInvoked.verify(getRequestedFor(urlMatching("/resource")));
    }


}