package berlin.yuna.paginator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import static berlin.yuna.paginator.config.Constants.CACHE_ITEM_LIMIT;
import static berlin.yuna.paginator.config.Constants.DEFAULT_PAGE_CACHE_MS;

public class CacheStatistic {

    @JsonProperty("size")
    private Long size;
    @JsonProperty("page_cache_ms_default")
    private Long maxLifeTime = DEFAULT_PAGE_CACHE_MS;
    @JsonProperty("size_limit")
    private Long sizeLimit = CACHE_ITEM_LIMIT;

    public Long getSize() {
        return size;
    }

    public CacheStatistic setSize(final Long size) {
        this.size = size;
        return this;
    }

    public Long getMaxLifeTime() {
        return maxLifeTime;
    }

    public CacheStatistic setMaxLifeTime(final Long maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
        return this;
    }

    public Long getSizeLimit() {
        return sizeLimit;
    }

    public CacheStatistic setSizeLimit(final Long sizeLimit) {
        this.sizeLimit = sizeLimit;
        return this;
    }

    @Override
    public String toString() {
        return "CacheStatistic{" +
                "size=" + size +
                ", maxLifeTime=" + maxLifeTime +
                ", sizeLimit=" + sizeLimit +
                '}';
    }
}
