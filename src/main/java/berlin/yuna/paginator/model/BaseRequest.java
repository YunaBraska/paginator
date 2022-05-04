package berlin.yuna.paginator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import static berlin.yuna.paginator.config.Constants.DEFAULT_PAGE_CACHE_MS;

public class BaseRequest {

    @JsonProperty("url")
    protected String url;
    @JsonProperty("page_cache_ms")
    protected Long pageCacheMS;

    public String getUrl() {
        return url;
    }

    public BaseRequest setUrl(final String url) {
        this.url = url;
        return this;
    }

    public Long getPageCacheMS() {
        return pageCacheMS == null || pageCacheMS < 0 ? DEFAULT_PAGE_CACHE_MS : pageCacheMS;
    }

    public void setPageCacheMS(final Long pageCacheMS) {
        this.pageCacheMS = pageCacheMS;
    }
}
