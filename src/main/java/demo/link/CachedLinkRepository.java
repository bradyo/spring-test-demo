package demo.link;

import demo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class CachedLinkRepository implements Repository<Link, Long> {

    private final EntityManager entityManager;
    private final Cache cache;

    @Autowired
    public CachedLinkRepository(EntityManager entityManager, Cache cache) {
        this.entityManager = entityManager;
        this.cache = cache;
    }

    @Override
    public Link findOne(Long id) {
        Link found = cache.get(id, Link.class);
        if (found != null) {
            return found;
        }

        Link link = entityManager.find(Link.class, id);
        if (link != null) {
            cache.put(id, link);
        }
        return link;
    }

    @Override
    public Link save(Link link) {
        entityManager.persist(link);
        return link;
    }
}
