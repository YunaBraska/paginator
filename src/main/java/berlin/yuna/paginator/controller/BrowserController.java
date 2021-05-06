package berlin.yuna.paginator.controller;

import berlin.yuna.paginator.model.CacheStatistic;
import berlin.yuna.paginator.model.ElementsRequest;
import berlin.yuna.paginator.model.ElementsResponse;
import berlin.yuna.paginator.model.GetPageRequest;
import berlin.yuna.paginator.model.SavePageRequest;
import berlin.yuna.paginator.service.BrowserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController()
@RequestMapping("/pages")
public class BrowserController {

    private final BrowserService browser;

    public BrowserController(final BrowserService browser) {
        this.browser = browser;
    }

    @RequestMapping(method = {GET, PUT}, produces = APPLICATION_JSON_VALUE)
    public String getPage(@RequestBody final GetPageRequest request) {
        return browser.getPage(request.getUrl());
    }

    @RequestMapping(method = {GET, PUT}, path = "elements", produces = APPLICATION_JSON_VALUE)
    public Map<String, List<ElementsResponse>> getElements(@RequestBody final ElementsRequest request) {
        return browser.getHtmlElements(request.getUrl(), request.cssQueries());
    }

    @RequestMapping(method = {GET, PUT}, path = "statistics", produces = APPLICATION_JSON_VALUE)
    public CacheStatistic getStatistics() {
        return browser.getStatistic();
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public String savePage(@RequestBody final SavePageRequest request) {
        return browser.addToCache(request.getUrl(), request.getContent());
    }

}
