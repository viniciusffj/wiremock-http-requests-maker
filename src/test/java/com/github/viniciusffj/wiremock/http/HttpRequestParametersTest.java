package com.github.viniciusffj.wiremock.http;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.viniciusffj.wiremock.helpers.ParametersBuilder;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
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
        Parameters parameters = new ParametersBuilder()
                .header("Content-Type", "application/json")
                .header("Location", "http://some.location.com")
                .build();

        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(parameters);

        Map<String, String> headers = httpRequestParameters.getHeaders();

        assertThat(headers.size(), is(2));
        assertThat(headers.get("Content-Type"), is("application/json"));
        assertThat(headers.get("Location"), is("http://some.location.com"));
    }

    @Test
    public void get_body_should_return_empty_body_when_body_not_present() throws Exception {
        Parameters parameters = new ParametersBuilder().build();

        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(parameters);

        assertThat(httpRequestParameters.getBody(), is(""));
    }

    @Test
    public void get_body_should_return_body_when_body_is_present() throws Exception {
        String body = "param1=value1&param2=value2";
        Parameters parameters = new ParametersBuilder()
                .body(body)
                .build();

        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(parameters);

        assertThat(httpRequestParameters.getBody(), is(body));
    }

    @Test
    public void to_string_should_contains_url() throws Exception {
        Parameters parameters = new ParametersBuilder()
                .url("http://localhost:8080")
                .build();
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(parameters);

        assertThat(httpRequestParameters.toString(), containsString("\"url\":\"http://localhost:8080\""));
    }

    @Test
    public void to_string_should_contains_method() throws Exception {
        Parameters parameters = new ParametersBuilder()
                .method(HttpMethod.PATCH)
                .build();
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(parameters);

        assertThat(httpRequestParameters.toString(), containsString("\"method\":\"PATCH\""));
    }

    @Test
    public void to_string_should_contains_body() throws Exception {
        Parameters parameters = new ParametersBuilder()
                .body("body")
                .build();
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(parameters);

        assertThat(httpRequestParameters.toString(), containsString("\"body\":\"body\""));
    }

    @Test
    public void to_string_should_contains_headers() throws Exception {
        Parameters parameters = new ParametersBuilder()
                .header("header_a", "value1")
                .header("header_b", "value2")
                .build();
        HttpRequestParameters httpRequestParameters = new HttpRequestParameters(parameters);

        assertThat(httpRequestParameters.toString(), containsString("\"header_a\":\"value1\""));
        assertThat(httpRequestParameters.toString(), containsString("\"header_b\":\"value2\""));
    }
}