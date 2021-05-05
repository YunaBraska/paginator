package berlin.yuna.paginator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import static berlin.yuna.paginator.config.Constants.CACHE_ITEM_LIMIT;
import static berlin.yuna.paginator.config.Constants.CACHE_LIVE_TIME_MS;

public class CacheStatistic {

    @JsonProperty("size")
    private Long size;
    @JsonProperty("max_life_time")
    private Long maxLifeTime = CACHE_LIVE_TIME_MS;
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
