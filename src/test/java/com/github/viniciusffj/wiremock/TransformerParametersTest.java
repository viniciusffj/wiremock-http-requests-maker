package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
        TransformerParameters transformerParameters = new TransformerParameters(Parameters.one("http_request_maker", "value"));

        assertThat(transformerParameters.hasRequestMakerParameter(), is(true));
    }

    @Test
    public void should_get_url_value() throws Exception {
        String url = "http://some.url.com";
        Parameters parameters = Parameters.one("http_request_maker", ImmutableMap.of("url", url));
        TransformerParameters transformerParameters = new TransformerParameters(parameters);

        assertThat(transformerParameters.getUrl(), is(url));
    }
}