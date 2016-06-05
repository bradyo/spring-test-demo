package demo.link;

import demo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class EncapsulatedLinkRepository implements Repository<Link, Long> {

    private final EntityManager entityManager;
    private final Cache localCache;

    @Autowired
    public EncapsulatedLinkRepository(EntityManager entityManager) {
        this.entityManager = entityManager;

        // This local cache is owned by this class. Control is not given to the caller
        // of this class via constructor injection. We are encapsulating the dependency so
        // consumers (including tests) do not need to know about it.
        this.localCache = new ConcurrentMapCache("links");
    }

    @Override
    public Link findOne(Long id) {
        Link found = localCache.get(id, Link.class);
        if (found != null) {
            return found;
        }

        Link link = entityManager.find(Link.class, id);
        if (link != null) {
            localCache.put(id, link);
        }
        return link;
    }

    @Override
    public Link save(Link link) {
        entityManager.persist(link);
        return link;
    }
}
