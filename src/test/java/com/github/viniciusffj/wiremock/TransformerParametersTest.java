package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.viniciusffj.wiremock.helpers.ParametersBuilder;
import com.github.viniciusffj.wiremock.http.HttpMethod;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TransformerParametersTest {

    @Test
    public void should_has_no_parameters_when_parameters_is_null() throws Exception {
        TransformerParameters transformerParameters = new TransformerParameters(null);

        assertThat(transformerParameters.hasRequestMakerParameter(), is(false));
    }

    @Test
    public void should_has_no_parameters_when_http_request_maker_is_not_present() throws Exception {
        TransformerParameters transformerParameters = new TransformerParameters(Parameters.empty());

        assertThat(transformerParameters.hasRequestMakerParameter(), is(false));
    }

    @Test
    public void should_has_parameters_when_http_request_maker_is_present() throws Exception {
        TransformerParameters transformerParameters = new TransformerParameters(new ParametersBuilder().build());

        assertThat(transformerParameters.hasRequestMakerParameter(), is(true));
    }

    @Test
    public void should_get_url_value() throws Exception {
        String url = "http://some.url.com";
        TransformerParameters transformerParameters = new TransformerParameters(new ParametersBuilder().url(url).build());

        assertThat(transformerParameters.getUrl(), is(url));
    }

    @Test
    public void should_get_method_value() throws Exception {
        TransformerParameters transformerParameters = new TransformerParameters(new ParametersBuilder().method(HttpMethod.POST).build());

        assertThat(transformerParameters.getMethod(), is(HttpMethod.POST));
    }

    @Test
    public void get_headers_should_return_empty_map_when_no_header_is_present() throws Exception {
        TransformerParameters transformerParameters = new TransformerParameters(new ParametersBuilder().build());

        assertThat(transformerParameters.getHeaders().isEmpty(), is(true));
    }

    @Test
    public void get_headers_should_headers_when_present() throws Exception {
        Parameters build = new ParametersBuilder()
                .header("Content-Type", "application/json")
                .header("Location", "http://some.location.com")
                .build();

        TransformerParameters transformerParameters = new TransformerParameters(build);

        Map<String, String> headers = transformerParameters.getHeaders();

        assertThat(headers.size(), is(2));
        assertThat(headers.get("Content-Type"), is("application/json"));
        assertThat(headers.get("Location"), is("http://some.location.com"));
    }
}