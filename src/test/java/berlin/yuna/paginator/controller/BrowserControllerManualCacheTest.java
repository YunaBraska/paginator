package berlin.yuna.paginator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("IntegrationTest")
class BrowserControllerManualCacheTest extends BaseControllerTest {

    @Test
    @DisplayName("Manual page cache test")
    void setPageTest() throws JsonProcessingException {
        assertThat(callGetStatistic().getSize(), is(equalTo(0L)));
        callSetPage("someStrangeUrl", "myCustomContent");
        assertThat(callGetStatistic().getSize(), is(equalTo(1L)));
        final String result = callGetPage("someStrangeUrl");
        assertThat(result, is(notNullValue()));
        assertThat(result, is(equalTo("myCustomContent")));
        assertThat(callGetStatistic().getSize(), is(equalTo(1L)));
    }
}