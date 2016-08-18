package demo.link;

import demo.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class LinkRepositoryTest extends AbstractLinkRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Repository<Link, Long> createRepository() {
        return new LinkRepository(entityManager);
    }

}
