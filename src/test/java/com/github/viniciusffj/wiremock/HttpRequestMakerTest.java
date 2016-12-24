package com.github.viniciusffj.wiremock;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HttpRequestMakerTest {

    private HttpRequestMaker httpRequestMaker;

    @Before
    public void setUp() throws Exception {
        httpRequestMaker = new HttpRequestMaker();
    }

    @Test
    public void should_not_be_applied_globally() throws Exception {
        assertThat(httpRequestMaker.applyGlobally(), is(false));
    }

    @Test
    public void should_register_correct_name() throws Exception {
        assertThat(httpRequestMaker.getName(), is("http-request-maker"));

    }
}