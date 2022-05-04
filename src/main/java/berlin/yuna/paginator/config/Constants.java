package berlin.yuna.paginator.config;

import java.util.TimeZone;

public class Constants {

    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
    public static final long DEFAULT_PAGE_CACHE_MS = 10800000L;
    public static final long CACHE_ITEM_LIMIT = 10000;
}
