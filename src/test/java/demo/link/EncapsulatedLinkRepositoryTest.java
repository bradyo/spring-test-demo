package demo.link;

import demo.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class EncapsulatedLinkRepositoryTest extends AbstractLinkRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Repository<Link, Long> createRepository() {
        return new EncapsulatedLinkRepository(entityManager);
    }

}
