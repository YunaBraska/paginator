package berlin.yuna.paginator.service;

import berlin.yuna.paginator.model.CacheStatistic;
import berlin.yuna.paginator.model.ElementsResponse;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static berlin.yuna.paginator.config.Constants.CACHE_ITEM_LIMIT;
import static berlin.yuna.paginator.config.Constants.CACHE_LIVE_TIME_MS;
import static java.lang.String.format;
import static org.springframework.util.StringUtils.hasText;

@Service
public class BrowserService {

    private final AtomicReference<ChromeDriver> atomicDriver = new AtomicReference<>();
    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final Map<Long, String> cacheTimes = new ConcurrentHashMap<>();
    private static final Logger LOG = LoggerFactory.getLogger(BrowserService.class);

    public BrowserService() {
        start();
    }

    @Scheduled(fixedRate = 10000)
    public void removeOutdated() {
        final int[] deletedItems = new int[]{0, 0};
        final long currentTimeMs = System.currentTimeMillis();
        new HashMap<>(cacheTimes).forEach((cachedTimeMs, url) -> {
            if (cachedTimeMs + CACHE_LIVE_TIME_MS < currentTimeMs) {
                deletedItems[0] = deletedItems[0] + 1;
                cache.remove(url);
                cacheTimes.remove(cachedTimeMs);
            }
        });
        while (cache.size() > CACHE_ITEM_LIMIT) {
            deletedItems[1] = deletedItems[1] + 1;
            final Map.Entry<Long, String> next = cacheTimes.entrySet().iterator().next();
            cache.remove(next.getValue());
            cacheTimes.remove(next.getKey());
        }
        logDeletedItems(deletedItems);
    }

    public synchronized void clearBrowserCache() {
        try {
            Optional.ofNullable(atomicDriver.get()).ifPresent(RemoteWebDriver::close);
            Optional.ofNullable(atomicDriver.get()).ifPresent(RemoteWebDriver::quit);
            WebDriverManager.chromedriver().clearResolutionCache();
        } catch (Exception e) {
            LOG.warn("Browser quit error", e);
        } finally {
            atomicDriver.set(null);
        }
    }

    public String addToCache(final String url, final String content) {
        if (hasText(url) && hasText(content)) {
            cache.put(url, content);
            cacheTimes.put(System.currentTimeMillis(), url);
        } else {
            LOG.warn("Received empty page from url [" + url + "]");
        }
        return content;
    }

    public Map<String, List<ElementsResponse>> getHtmlElements(final String url, final Map<String, String> cssQueryMap) {
        final String html = getPage(url);
        final Map<String, List<ElementsResponse>> result = new HashMap<>();
        if (hasText(html)) {
            final Document document = Jsoup.parse(html);
            cssQueryMap.forEach((name, cssQuery) -> {
                if (hasText(name) && hasText(cssQuery)) {
                    result.put(name, ElementsResponse.from(document.select(cssQuery)));
                }
            });
        }
        return result;
    }

    public String getPage(final String url) {
        return getCachedPage(url).orElseGet(() -> cacheNewPage(url));
    }

    public CacheStatistic getStatistic() {
        return new CacheStatistic().setSize((long) cache.size());
    }

    private Optional<String> getCachedPage(final String url) {
        return Optional.ofNullable(cache.get(url));
    }

    private synchronized String cacheNewPage(final String url) {
        if (toUrl(url) != null) {
            try {
                if (atomicDriver.get() == null) {
                    start();
                }
                LOG.debug(format("Page call [%s]", url));
                final ChromeDriver driver = this.atomicDriver.get();
                driver.get(url);
                return addToCache(url, driver.getPageSource());
            } catch (Exception e) {
                LOG.warn(format("Could not render [%s]", url), e);
                clearBrowserCache();
            }
        }
        return "";
    }

    private void logDeletedItems(final int[] deletedItems) {
        if ((deletedItems[0] + deletedItems[1]) != 0) {
            final String logMessage = format(
                    "Deleted items [%s], [%s] items CACHE_LIVE_TIME_MS [%s], [%s] items CACHE_ITEM_LIMIT [%s]",
                    (deletedItems[0] + deletedItems[1]),
                    deletedItems[0],
                    CACHE_LIVE_TIME_MS,
                    deletedItems[1],
                    CACHE_ITEM_LIMIT
            );
            if (deletedItems[1] > 0) {
                LOG.warn(logMessage);
            } else {
                LOG.info(logMessage);
            }
        }
    }

    private synchronized void start() {
        System.setProperty("webdriver.chrome.whitelistedIps", "");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        WebDriverManager.chromedriver().setup();
        atomicDriver.set(new ChromeDriver(new ChromeOptions()
                .setHeadless(true)
                .addArguments("--no-sandbox")
                .addArguments("--disable-extensions")
                .addArguments("--disable-dev-shm-usage")
        ));
    }

    private URL toUrl(final String url) {
        if (!hasText(url)) {
            return null;
        }
        try {
            return new URL(url);
        } catch (MalformedURLException ignored) {
            return null;
        }
    }

}
