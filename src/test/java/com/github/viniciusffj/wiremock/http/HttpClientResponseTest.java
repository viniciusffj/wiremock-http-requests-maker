package com.github.viniciusffj.wiremock.http;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HttpClientResponseTest {

    @Test
    public void should_build_error_response() throws Exception {
        Throwable throwable = new Throwable();
        HttpClientResponse response = HttpClientResponse.error(throwable);

        assertThat(response.hasError(), is(true));
        assertThat(response.getError(), is(throwable));
    }

    @Test
    public void should_build_successful_responses() throws Exception {
        HttpClientResponse response = HttpClientResponse.success(200, "{}");

        assertThat(response.hasError(), is(false));
        assertThat(response.getStatus(), is(200));
        assertThat(response.getBody(), is("{}"));
    }
}