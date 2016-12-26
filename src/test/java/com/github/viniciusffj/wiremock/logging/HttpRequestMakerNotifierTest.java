package com.github.viniciusffj.wiremock.logging;

import com.github.tomakehurst.wiremock.common.Notifier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    public void should_log_when_has_parameters() throws Exception {
        httpRequestMakerNotifier.hasParameters();

        verify(notifier, times(1)).info("[http-request-maker]: Parameters were passed");
    }

    @Test
    public void should_log_when_has_no_parameters() throws Exception {
        httpRequestMakerNotifier.hasNoParameters();

        verify(notifier, times(1)).info("[http-request-maker]: No parameters were passed");
    }
}