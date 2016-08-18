package demo.link;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class CachedLinkRepositoryFactory {

    public CachedLinkRepository create(EntityManager entityManager) {
        Cache cache = new ConcurrentMapCache("links");

        return new CachedLinkRepository(entityManager, cache);
    }
}
