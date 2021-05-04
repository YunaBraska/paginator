package berlin.yuna.paginator.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class BrowserControllerTest extends BaseControllerTest {

    @LocalServerPort
    int port;

    @Test
    @DisplayName("Page call test")
    void getPageTest() throws JsonProcessingException {
        assertThat(callGetStatistic().getSize(), is(0));
        final String result = callGetPage("http://example.com");
        assertThat(result, is(notNullValue()));
        assertThat(callGetStatistic().getSize(), is(1));
    }
}