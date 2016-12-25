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

public class HttpRequestMakerIntegrationTest {

    @ClassRule
    public static WireMockClassRule serviceToBeInvoked = new WireMockClassRule(options().port(8011));

    @ClassRule
    public static WireMockClassRule serviceWithRequestMaker =
            new WireMockClassRule(options()
                    .port(8012)
                    .extensions("com.github.viniciusffj.wiremock.HttpRequestMaker"));

    @Before
    public void setUp() throws Exception {
        serviceToBeInvoked.
                stubFor(get(urlEqualTo("/resource"))
                        .willReturn(aResponse()
                                .withStatus(200)));

        serviceToBeInvoked.
                stubFor(post(urlEqualTo("/resource"))
                        .willReturn(aResponse()
                                .withStatus(200)));

        serviceWithRequestMaker
                .stubFor(get(urlEqualTo("/http-request-maker-get"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withTransformerParameter("http_request_maker", getRequestParameters())
                                .withTransformers("http-request-maker")));

        serviceWithRequestMaker
                .stubFor(post(urlEqualTo("/http-request-maker-post"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withTransformerParameter("http_request_maker", postRequestParameters())
                                .withTransformers("http-request-maker")));
    }

    private Map<String, String> getRequestParameters() {
        return new ParametersBuilder()
                .url("http://localhost:8011/resource")
                .method(HttpMethod.GET)
                .buildHttpRequestMakerParameters();
    }


    private Map<String, String> postRequestParameters() {
        return new ParametersBuilder()
                .url("http://localhost:8011/resource")
                .method(HttpMethod.POST)
                .buildHttpRequestMakerParameters();
    }

    @Test
    public void should_make_get_request_other_service() throws Exception {
        RestAssured
                .get("http://localhost:8012/http-request-maker-get")
                .then()
                .assertThat()
                .statusCode(200);

        serviceToBeInvoked.verify(getRequestedFor(urlMatching("/resource")));
    }

    @Test
    public void should_make_post_request_to_other_service() throws Exception {
        RestAssured
                .post("http://localhost:8012/http-request-maker-post")
                .then()
                .assertThat()
                .statusCode(200);

        serviceToBeInvoked.verify(postRequestedFor(urlMatching("/resource")));
    }
}