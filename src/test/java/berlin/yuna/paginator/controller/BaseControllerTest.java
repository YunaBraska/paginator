package berlin.yuna.paginator.controller;


import berlin.yuna.paginator.model.CacheStatistic;
import berlin.yuna.paginator.model.ElementsRequest;
import berlin.yuna.paginator.model.ElementsResponse;
import berlin.yuna.paginator.model.SavePageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.OK;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseControllerTest {

    @LocalServerPort
    public int port;

    public ObjectMapper mapper = new ObjectMapper();

    public String callGetPage(final String url) {
        return restClient()
                .body(asString(new SavePageRequest().setUrl(url)))
                .get("/pages")
                .then().assertThat().statusCode(OK.value())
                .extract()
                .asString();
    }

    public Map<String, List<ElementsResponse>> callGetElements(final String url, final Map<String, String> cssQuery) throws JsonProcessingException {
        return mapper.readValue(restClient()
                .body(asString(new ElementsRequest().setUrl(url).setCssQueries(cssQuery)))
                .get("/pages/elements")
                .then().assertThat().statusCode(OK.value())
                .extract()
                .asString(), new TypeReference<Map<String, List<ElementsResponse>>>() {
        });
    }

    public void callSetPage(final String url, final String content) {
        restClient()
                .body(asString(new SavePageRequest().setUrl(url).setContent(content)))
                .post("/pages").then()
                .assertThat().statusCode(OK.value());
    }

    public CacheStatistic callGetStatistic() throws JsonProcessingException {
        return mapper.readValue(restClient()
                .get("/pages/statistics")
                .then().assertThat().statusCode(OK.value())
                .extract()
                .asString(), CacheStatistic.class);
    }

    public RequestSpecification restClient() {
        return given().port(port).log().all()
                .contentType(JSON)
                .filter(new ResponseLoggingFilter());
    }

    private String asString(final Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}