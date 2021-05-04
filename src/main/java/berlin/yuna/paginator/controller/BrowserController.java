package berlin.yuna.paginator.controller;

import berlin.yuna.paginator.service.BrowserService;
import berlin.yuna.paginator.service.BrowserService.CacheStatistic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController()
@RequestMapping("/pages")
public class BrowserController {

    private final BrowserService browser;

    public BrowserController(final BrowserService browser) {
        this.browser = browser;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public String getPage(@RequestBody final GetPageRequest request) {
        return browser.getPage(request.getUrl());
    }

    @GetMapping(path = "statistics", produces = APPLICATION_JSON_VALUE)
    public CacheStatistic getStatistics() {
        return browser.getStatistic();
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public String savePage(@RequestBody final PostPageRequest request) {
        return browser.addToCache(request.getUrl(), request.getContent());
    }

    public static class GetPageRequest {
        private String url;

        public String getUrl() {
            return url;
        }

        public GetPageRequest setUrl(final String url) {
            this.url = url;
            return this;
        }
    }

    public static class PostPageRequest {
        private String url;
        private String content;

        public String getUrl() {
            return url;
        }

        public PostPageRequest setUrl(final String url) {
            this.url = url;
            return this;
        }

        public String getContent() {
            return content;
        }

        public PostPageRequest setContent(final String content) {
            this.content = content;
            return this;
        }
    }
}
