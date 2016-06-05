package demo.link;

import demo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import javax.persistence.EntityManager;

public class CachedLinkRepositoryTest extends AbstractLinkRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Cache linkCache;

    @Override
    public Repository<Link, Long> createRepository() {
        return new CachedLinkRepository(entityManager, linkCache);
    }

}
