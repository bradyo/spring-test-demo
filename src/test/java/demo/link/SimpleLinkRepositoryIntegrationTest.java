package demo.link;

import demo.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class SimpleLinkRepositoryIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    private SimpleLinkRepository simpleLinkRepository;

    @Before
    public void before() {
        simpleLinkRepository = new SimpleLinkRepository(entityManager);
    }

    @Test
    public void testGet() {
        Link link = simpleLinkRepository.save(newLink());

        Link actualLink = simpleLinkRepository.findOne(link.getId());

        assertThat(actualLink, is(link));
    }

    private Link newLink() {
        Link link = new Link();
        link.setToken("abcde");
        link.setShortUrl("https://domain.com/abcde");
        link.setFullUrl("https://google.com/?q=hello+world");
        return link;
    }
}
