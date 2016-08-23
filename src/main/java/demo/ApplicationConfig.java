package demo;

import demo.link_shortening.bitly.BitlyLinkShorteningServiceFactory;
import demo.link_shortening.bitly.BitlySettings;
import demo.link_shortening.LinkShorteningService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class ApplicationConfig {

    public static final String LINKS_CACHE_NAME = "links";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(LINKS_CACHE_NAME);
    }

    @Bean
    public Cache shortLinksCache(CacheManager cacheManager) {
        return cacheManager.getCache(LINKS_CACHE_NAME);
    }

    @Bean
    public LinkShorteningService linkShorteningService(
        BitlyLinkShorteningServiceFactory bitlyLinkShorteningServiceFactory,
        BitlySettings bitlySettings
    ) {
        return bitlyLinkShorteningServiceFactory.create(bitlySettings.getBaseUrl(), bitlySettings.getAccessToken());
    }
}
