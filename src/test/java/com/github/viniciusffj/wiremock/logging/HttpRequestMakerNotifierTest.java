package com.github.viniciusffj.wiremock.logging;

import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.viniciusffj.wiremock.helpers.HttpRequestParametersBuilder;
import com.github.viniciusffj.wiremock.helpers.HttpResponseBuilder;
import com.github.viniciusffj.wiremock.http.HttpClientResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestMakerNotifierTest {

    @Mock
    private Notifier notifier;

    private HttpRequestMakerNotifier httpRequestMakerNotifier;

    @Before
    public void setUp() throws Exception {
        httpRequestMakerNotifier = new HttpRequestMakerNotifier(notifier);
    }

    @Test
    public void should_log_when_attempting_request() throws Exception {
        httpRequestMakerNotifier.requestAttempt(new HttpRequestParametersBuilder().build());

        verify(notifier, times(1)).info(contains("[http-request-maker]: Request attempt:"));
    }

    @Test
    public void should_log_when_has_no_parameters() throws Exception {
        httpRequestMakerNotifier.hasNoParameters();

        verify(notifier, times(1)).info("[http-request-maker]: No parameters were passed");
    }

    @Test
    public void should_log_successful_http_response() throws Exception {
        HttpClientResponse response = new HttpResponseBuilder().success();

        httpRequestMakerNotifier.successfulHttpResponse(response);

        String message = String.format("[http-request-maker]: Http response: {status=%s, body=%s}", response.getStatus(), response.getBody());
        verify(notifier, times(1)).info(message);
    }
}