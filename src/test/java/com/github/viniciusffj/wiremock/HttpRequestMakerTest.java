package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.viniciusffj.wiremock.helpers.HttpResponseBuilder;
import com.github.viniciusffj.wiremock.helpers.ParametersBuilder;
import com.github.viniciusffj.wiremock.http.HttpClient;
import com.github.viniciusffj.wiremock.http.HttpClientResponse;
import com.github.viniciusffj.wiremock.http.HttpMethod;
import com.github.viniciusffj.wiremock.http.HttpRequestParameters;
import com.github.viniciusffj.wiremock.logging.HttpRequestMakerNotifier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestMakerTest {

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpRequestMakerNotifier notifier;

    private HttpRequestMaker httpRequestMaker;

    @Before
    public void setUp() throws Exception {
        httpRequestMaker = new HttpRequestMaker(httpClient, notifier);
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

        verify(httpClient, never()).execute(any(HttpRequestParameters.class));
        verify(notifier, times(1)).hasNoParameters();
    }

    @Test
    public void should_make_get_call_when_has_url_parameter() throws Exception {
        HttpClientResponse response = new HttpResponseBuilder().success();
        when(httpClient.execute(any(HttpRequestParameters.class))).thenReturn(response);

        Parameters parameters = new ParametersBuilder()
                .url("http://localhost:9000")
                .method(HttpMethod.OPTIONS)
                .build();

        httpRequestMaker.transform(null, ResponseDefinition.ok(), null, parameters);

        verify(notifier, times(1)).requestAttempt(any(HttpRequestParameters.class));

        verify(httpClient, times(1)).execute(any(HttpRequestParameters.class));

        verify(notifier, times(1)).successfulHttpResponse(response);
    }

    @Test
    public void should_log_when_http_request_has_error() throws Exception {
        HttpClientResponse response = new HttpResponseBuilder().error();
        when(httpClient.execute(any(HttpRequestParameters.class))).thenReturn(response);

        Parameters parameters = new ParametersBuilder()
                .url("http://localhost:9002")
                .method(HttpMethod.DELETE)
                .build();

        httpRequestMaker.transform(null, ResponseDefinition.ok(), null, parameters);

        verify(notifier, times(1)).requestAttempt(any(HttpRequestParameters.class));

        verify(httpClient, times(1)).execute(any(HttpRequestParameters.class));

        verify(notifier, times(1)).errorHttpResponse(response);
    }
}