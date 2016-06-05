package demo.link;

import demo.Application;
import demo.Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
abstract public class AbstractLinkRepositoryTest {

    private Repository<Link, Long> repository;

    abstract Repository<Link, Long> createRepository();

    @Before
    public void before() {
        repository = createRepository();
    }

    @Test
    public void testGet() {
        Link link = repository.save(newLink());

        Link actualLink = repository.findOne(link.getId());

        assertThat(actualLink, is(link));
    }

    @Test
    public void testGetNonExisting() {
        Link actualLink = repository.findOne(1L);

        assertThat(actualLink, is(nullValue()));
    }

    private Link newLink() {
        Link link = new Link();
        link.setToken("abcde");
        link.setShortUrl("https://domain.com/abcde");
        link.setFullUrl("https://google.com/?q=hello+world");
        return link;
    }
}
