package berlin.yuna.paginator.service;

import berlin.yuna.paginator.model.BaseRequest;
import berlin.yuna.paginator.model.CacheItem;
import berlin.yuna.paginator.model.CacheStatistic;
import berlin.yuna.paginator.model.ElementsRequest;
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

import java.io.Closeable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static berlin.yuna.paginator.config.Constants.CACHE_ITEM_LIMIT;
import static berlin.yuna.paginator.config.Constants.DEFAULT_PAGE_CACHE_MS;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

@Service
public class BrowserService implements Closeable {

    private final AtomicReference<ChromeDriver> atomicDriver = new AtomicReference<>();
    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final List<CacheItem> cacheTimes = new CopyOnWriteArrayList<>(); //Better: ConcurrentLinkedQueue?
    private static final Logger LOG = LoggerFactory.getLogger(BrowserService.class);

    public BrowserService() {
        start();
    }

    @Scheduled(fixedRate = 10000)
    public void removeOutdated() {
        logDeletedItems(cleanUpOverTimed(), cleanUpOverflow()); //Order is important
    }

    public synchronized void clearBrowserCache() {
        close();
    }

    public String addToCache(final BaseRequest request, final String content) {
        if (hasText(request.getUrl()) && hasText(content)) {
            cache.put(request.getUrl(), content);
            registerTimeoutIfNew(request);
        } else {
            LOG.warn("Received empty page from url [{}]", request.getUrl());
        }
        return content;
    }

    public Map<String, List<ElementsResponse>> getHtmlElements(final ElementsRequest request) {
        final String html = getPage(request);
        final Map<String, List<ElementsResponse>> result = new HashMap<>();
        if (hasText(html)) {
            final Document document = Jsoup.parse(html);
            request.cssQueries().forEach((name, cssQuery) -> {
                if (hasText(name) && hasText(cssQuery)) {
                    result.put(name, ElementsResponse.from(document.select(cssQuery)));
                }
            });
        }
        return result;
    }

    public String getPage(final BaseRequest request) {
        return getCachedPage(request.getUrl()).orElseGet(() -> cacheNewPage(request));
    }

    public CacheStatistic getStatistic() {
        return new CacheStatistic().setSize((long) cache.size());
    }

    private void registerTimeoutIfNew(final BaseRequest request) {
        final CacheItem cacheItem = new CacheItem(System.currentTimeMillis() + request.getPageCacheMS(), request.getUrl());
        final int index = cacheTimes.indexOf(cacheItem);
        if (index < 0) {
            cacheTimes.add(cacheItem);
        }
    }

    private Optional<String> getCachedPage(final String url) {
        return Optional.ofNullable(cache.get(url));
    }

    private synchronized String cacheNewPage(final BaseRequest request) {
        if (toUrl(request.getUrl()) != null) {
            try {
                if (atomicDriver.get() == null) {
                    start();
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug(format("Page call [%s]", request.getUrl()));
                }
                final ChromeDriver driver = this.atomicDriver.get();
                driver.get(request.getUrl());
                return addToCache(request, driver.getPageSource());
            } catch (Exception e) {
                LOG.warn(format("Could not render [%s]", request.getUrl()), e);
                clearBrowserCache();
            }
        }
        return "";
    }

    private long cleanUpOverTimed() {
        final AtomicLong deletedItems = new AtomicLong(0);
        final long currentTimeMs = System.currentTimeMillis();
        new ArrayList<>(cacheTimes).forEach(cacheItem -> {
            if (cacheItem.time() < currentTimeMs) {
                deletedItems.incrementAndGet();
                cache.remove(cacheItem.id());
                cacheTimes.remove(cacheItem);
            }
        });
        return deletedItems.get();
    }

    private long cleanUpOverflow() {
        final AtomicLong deletedItems = new AtomicLong(0);
        final long deleteNumber = cache.size() - CACHE_ITEM_LIMIT;
        if (deleteNumber > 0) {
            final List<CacheItem> collect = cacheTimes
                    .stream()
                    .sorted((o1, o2) -> Long.compare(o2.time(), o1.time()))
                    .limit(deleteNumber)
                    .collect(toList());
            collect
                    .forEach(cacheItem -> {
                        deletedItems.incrementAndGet();
                        cache.remove(cacheItem.id());
                    });
            cacheTimes.removeAll(collect);
        }
        return deletedItems.get();
    }

    private void logDeletedItems(final long overTimed, final long overFlow) {
        if ((overTimed + overFlow) != 0) {
            final String logMessage = format(
                    "Deleted items [%s], [%s] items CACHE_LIVE_TIME_MS [%s], [%s] items CACHE_ITEM_LIMIT [%s]",
                    (overTimed + overFlow),
                    overTimed,
                    DEFAULT_PAGE_CACHE_MS,
                    overFlow,
                    CACHE_ITEM_LIMIT
            );
            LOG.info(logMessage);
        }
    }

    private synchronized void start() {
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

    @Override
    public synchronized void close() {
        try {
            Optional.ofNullable(atomicDriver.get()).ifPresent(RemoteWebDriver::quit);
            WebDriverManager.chromedriver().clearResolutionCache();
        } catch (Exception e) {
            LOG.warn("Browser quit error", e);
        } finally {
            atomicDriver.set(null);
        }
    }
}
