package demo.link;

import demo.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CachedLinkRepositoryTest extends AbstractLinkRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CachedLinkRepositoryFactory cachedLinkRepositoryFactory;

    @Override
    public Repository<Link, Long> createRepository() {
        return cachedLinkRepositoryFactory.create(entityManager);
    }

}
