package demo.link;

import demo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class LinkRepository implements Repository<Link, Long> {

    private final EntityManager entityManager;

    @Autowired
    public LinkRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Link findOne(Long id) {
        return entityManager.find(Link.class, id);
    }

    @Override
    public Link save(Link link) {
        entityManager.persist(link);
        return link;
    }
}
