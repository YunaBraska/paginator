package berlin.yuna.paginator.model;

import java.util.Objects;

import static berlin.yuna.paginator.config.Constants.DEFAULT_PAGE_CACHE_MS;

public class CacheItem {

    private final long time;
    private final String id;

    public CacheItem(final long time, final String id) {
        this.time = time < 0 ? System.currentTimeMillis() + DEFAULT_PAGE_CACHE_MS : time;
        this.id = id;
    }

    public long time() {
        return time;
    }

    public String id() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CacheItem cacheItem = (CacheItem) o;
        return Objects.equals(id, cacheItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CacheItem{" +
                "time=" + time +
                ", id='" + id + '\'' +
                '}';
    }
}
