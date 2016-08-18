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
public class LinkRepositoryIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    private LinkRepository linkRepository;

    @Before
    public void before() {
        linkRepository = new LinkRepository(entityManager);
    }

    @Test
    public void testGet() {
        Link link = linkRepository.save(newLink());

        Link actualLink = linkRepository.findOne(link.getId());

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
