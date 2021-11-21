package berlin.yuna.paginator.controller;

import berlin.yuna.paginator.model.ElementsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    @DisplayName("Find in document")
    void findInDocument() throws JsonProcessingException {
        callSetPage("parse.example.com", htmlExample);
        final Map<String, List<ElementsResponse>> result = callGetElements("parse.example.com", mapOf("form_text", "form p"));
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(equalTo(1)));
        assertThat(result.get("form_text"), is(notNullValue()));
        assertThat(result.get("form_text").size(), is(equalTo(2)));
    }

    public final Map<String, String> mapOf(final String... kv) {
        final HashMap<String, String> result = new HashMap<>();
        boolean isKey = true;
        String key = null;
        for (String property : kv) {
            if (isKey) {
                key = property;
            } else {
                result.put(key, property);
            }
            isKey = !isKey;
        }
        return result;
    }

    final static String htmlExample = "<!doctype html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <title>Example Domain</title>\n" +
            "    <meta charset=\"utf-8\" />\n" +
            "    <meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<div>\n" +
            "    <h1>Example Domain</h1>\n" +
            "    <p>This domain is for use in illustrative examples in documents. You may use this\n" +
            "    domain in literature without prior coordination or asking for permission.</p>\n" +
            "    <p><a href=\"https://www.iana.org/domains/example\">More information...</a></p>\n" +
            "<form action='http://example.com' method='get'>\n" +
            "        <P>Some example text here.</P>\n" +
            "        <input type='text' class='is-input' id='agent_name' name='deviceName' placeholder='Device Name'>\n" +
            "        <input type='hidden' name='p' value='firefox'>\n" +
            "        <input type='hidden' name='email' value='example@example.com'>\n" +
            "        <input type='hidden' name='k' value='cITBk236gyd56oiY0fhk6lpuo9nt61Va'>\n" +
            "        <p><input type='submit' class='btn-blue' style='margin-top:15px;' value='Install'></p>\n" +
            "</form>" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
}