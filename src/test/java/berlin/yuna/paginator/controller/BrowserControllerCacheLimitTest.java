package berlin.yuna.paginator.controller;


import berlin.yuna.paginator.service.BrowserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static berlin.yuna.paginator.config.Constants.CACHE_ITEM_LIMIT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
class BrowserControllerCacheLimitTest {

    @Test
    @DisplayName("cache overflow test")
    void setPageTest() {
        try (final BrowserService service = new BrowserService()) {
            for (int i = 0; i < (CACHE_ITEM_LIMIT + 10); i++) {
                service.addToCache(UUID.randomUUID().toString(), "cacheOverflowTest");
            }
            assertThat(service.getStatistic().getSize(), is(equalTo(CACHE_ITEM_LIMIT + 10)));
            service.removeOutdated();
            assertThat(service.getStatistic().getSize(), is(equalTo(CACHE_ITEM_LIMIT)));
        }
    }
}