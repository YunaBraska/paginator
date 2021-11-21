package berlin.yuna.paginator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ElementsRequest {

    @JsonProperty("url")
    private String url;
    @JsonProperty("css_queries")
    private Map<String, String> cssQueries = new HashMap<>();

    public ElementsRequest addCssQuery(final String id, final String cssQuery) {
        cssQueries.put(id, cssQuery);
        return this;
    }

    public Map<String, String> cssQueries() {
        return cssQueries;
    }

    public ElementsRequest setCssQueries(final Map<String, String> cssQueries) {
        this.cssQueries = cssQueries;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ElementsRequest setUrl(final String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "ElementsRequest{" +
                "url='" + url + '\'' +
                ", cssQueries=" + cssQueries +
                '}';
    }
}