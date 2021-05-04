package berlin.yuna.paginator.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class BrowserControllerInvalidUrlTest extends BaseControllerTest {

    @Test
    @DisplayName("Manual page cache test")
    void setPageTest() throws JsonProcessingException {
        assertThat(callGetStatistic().getSize(), is(0));
        final String result = callGetPage("invalidUrl");
        assertThat(result, is(notNullValue()));
        assertThat(result, is(equalTo("")));
        assertThat(callGetStatistic().getSize(), is(0));
    }
}