package berlin.yuna.paginator.controller;

import berlin.yuna.paginator.controller.BrowserController.SavePageRequest;
import berlin.yuna.paginator.service.BrowserService.CacheStatistic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpStatus.OK;

class BrowserControllerManualCacheTest extends BaseControllerTest{

    @Test
    @DisplayName("Manual page cache test")
    void setPageTest() throws JsonProcessingException {
        assertThat(callGetStatistic().getSize(), is(0));
        restClient()
                .body(mapper.writeValueAsString(new SavePageRequest().setUrl("someStrangeUrl").setContent("myCustomContent")))
                .post("/pages").then()
                .assertThat().statusCode(OK.value());
        assertThat(callGetStatistic().getSize(), is(1));
        final String result = callGetPage("someStrangeUrl");
        assertThat(result, is(notNullValue()));
        assertThat(result, is(equalTo("myCustomContent")));
        assertThat(callGetStatistic().getSize(), is(1));
    }
}