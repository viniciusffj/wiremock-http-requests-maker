package com.github.viniciusffj.wiremock.http;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HttpMethodTest {

    @Test
    public void should_return_get_method_from_string_in_any_case() throws Exception {
        assertThat(HttpMethod.fromString("get"), is(HttpMethod.GET));
    }

    @Test
    public void should_return_post_method_from_string() throws Exception {
        assertThat(HttpMethod.fromString("POST"), is(HttpMethod.POST));
    }

    @Test
    public void should_return_put_method_from_string() throws Exception {
        assertThat(HttpMethod.fromString("PuT"), is(HttpMethod.PUT));
    }

    @Test
    public void should_return_patch_method_from_string() throws Exception {
        assertThat(HttpMethod.fromString("PATCH"), is(HttpMethod.PATCH));
    }

    @Test
    public void should_return_delete_method_from_string() throws Exception {
        assertThat(HttpMethod.fromString("DeletE"), is(HttpMethod.DELETE));
    }

    @Test
    public void should_return_head_method_from_string_in_any_case() throws Exception {
        assertThat(HttpMethod.fromString("HeaD"), is(HttpMethod.HEAD));
    }

    @Test
    public void should_return_options_method_from_string_in_any_case() throws Exception {
        assertThat(HttpMethod.fromString("options"), is(HttpMethod.OPTIONS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_method_does_not_exist() throws Exception {
        HttpMethod.fromString("NO METHOD");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_parameter_is_null() throws Exception {
        HttpMethod.fromString(null);
    }
}