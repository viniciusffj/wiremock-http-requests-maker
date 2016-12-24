package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.viniciusffj.wiremock.helpers.ParametersBuilder;
import com.github.viniciusffj.wiremock.http.HttpClient;
import com.github.viniciusffj.wiremock.http.HttpClientResponse;
import com.github.viniciusffj.wiremock.http.HttpMethod;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestMakerTest {

    @Mock
    private HttpClient httpClient;

    private HttpRequestMaker httpRequestMaker;

    @Before
    public void setUp() throws Exception {
        httpRequestMaker = new HttpRequestMaker(httpClient);

        when(httpClient.execute(anyString(), eq(HttpMethod.GET))).thenReturn(HttpClientResponse.success());
    }

    @Test
    public void should_not_be_applied_globally() throws Exception {
        assertThat(httpRequestMaker.applyGlobally(), is(false));
    }

    @Test
    public void should_register_correct_name() throws Exception {
        assertThat(httpRequestMaker.getName(), is("http-request-maker"));
    }

    @Test
    public void should_not_make_get_call_when_no_parameter() throws Exception {
        httpRequestMaker.transform(null, ResponseDefinition.ok(), null, new ParametersBuilder().empty());

        verify(httpClient, never()).execute(anyString(), any(HttpMethod.class));
    }

    @Test
    public void should_make_get_call_when_has_url_parameter() throws Exception {
        String url = "http://localhost:9000";

        Parameters parameters = new ParametersBuilder()
                .url(url)
                .method(HttpMethod.GET)
                .build();

        httpRequestMaker.transform(null, ResponseDefinition.ok(), null, parameters);

        verify(httpClient, times(1)).execute(url, HttpMethod.GET);
    }
}