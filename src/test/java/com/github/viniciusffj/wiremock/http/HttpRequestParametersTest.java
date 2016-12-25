package com.github.viniciusffj.wiremock.http;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.viniciusffj.wiremock.helpers.ParametersBuilder;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HttpRequestParametersTest {

    @Test
    public void should_has_no_parameters_when_parameters_is_null() throws Exception {
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(null);

        assertThat(httpRequestParameters.hasRequestMakerParameter(), is(false));
    }

    @Test
    public void should_has_no_parameters_when_http_request_maker_is_not_present() throws Exception {
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(Parameters.empty());

        assertThat(httpRequestParameters.hasRequestMakerParameter(), is(false));
    }

    @Test
    public void should_has_parameters_when_http_request_maker_is_present() throws Exception {
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(new ParametersBuilder().build());

        assertThat(httpRequestParameters.hasRequestMakerParameter(), is(true));
    }

    @Test
    public void should_get_url_value() throws Exception {
        String url = "http://some.url.com";
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(new ParametersBuilder().url(url).build());

        assertThat(httpRequestParameters.getUrl(), is(url));
    }

    @Test
    public void should_get_method_value() throws Exception {
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(new ParametersBuilder().method(HttpMethod.POST).build());

        assertThat(httpRequestParameters.getMethod(), is(HttpMethod.POST));
    }

    @Test
    public void get_headers_should_return_empty_map_when_no_header_is_present() throws Exception {
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(new ParametersBuilder().build());

        assertThat(httpRequestParameters.getHeaders().isEmpty(), is(true));
    }

    @Test
    public void get_headers_should_headers_when_present() throws Exception {
        Parameters build = new ParametersBuilder()
                .header("Content-Type", "application/json")
                .header("Location", "http://some.location.com")
                .build();

        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(build);

        Map<String, String> headers = httpRequestParameters.getHeaders();

        assertThat(headers.size(), is(2));
        assertThat(headers.get("Content-Type"), is("application/json"));
        assertThat(headers.get("Location"), is("http://some.location.com"));
    }
}