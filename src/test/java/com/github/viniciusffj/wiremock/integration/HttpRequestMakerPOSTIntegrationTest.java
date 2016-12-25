package com.github.viniciusffj.wiremock.integration;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.viniciusffj.wiremock.helpers.ParametersBuilder;
import com.github.viniciusffj.wiremock.http.HttpMethod;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class HttpRequestMakerPOSTIntegrationTest {

    @ClassRule
    public static WireMockClassRule serviceToBeInvoked = new WireMockClassRule(options().port(8013));

    @ClassRule
    public static WireMockClassRule serviceWithRequestMaker =
            new WireMockClassRule(options()
                    .port(8014)
                    .extensions("com.github.viniciusffj.wiremock.HttpRequestMaker"));

    @Before
    public void setUp() throws Exception {
        serviceToBeInvoked.
                stubFor(post(urlEqualTo("/resource"))
                        .willReturn(aResponse()
                                .withStatus(200)));

        Map<String, Object> requestParameters = new ParametersBuilder()
                .url("http://localhost:8013/resource")
                .method(HttpMethod.POST)
                .buildHttpRequestMakerParameters();

        serviceWithRequestMaker
                .stubFor(post(urlEqualTo("/http-request-maker-post"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withTransformerParameter("http_request_maker", requestParameters)
                                .withTransformers("http-request-maker")));
    }

    @Test
    public void should_make_post_request_to_other_service() throws Exception {
        RestAssured
                .post("http://localhost:8014/http-request-maker-post")
                .then()
                .assertThat()
                .statusCode(200);

        serviceToBeInvoked.verify(postRequestedFor(urlMatching("/resource")));
    }
}