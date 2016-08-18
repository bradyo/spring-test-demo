package demo.link;

import demo.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class CachedLinkRepositoryIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    private Cache cache;

    private CachedLinkRepository cachedLinkRepository;

    @Before
    public void before() {
        cache = new ConcurrentMapCache("links");
        cachedLinkRepository = new CachedLinkRepository(entityManager, cache);
    }

    @Test
    public void testGet() {
        Link link = cachedLinkRepository.save(newLink());

        Link actualLink = cachedLinkRepository.findOne(link.getId());

        assertThat(actualLink, is(link));
    }

    /**
     * Since this is a black-box functional test, we need to assert on the observable behavior of the
     * method. Since this method includes caching, the observed behavior is performance.
     *
     * What we are actually want to test here is that subsequent gets perform better than first gets.
     * Our acceptance criteria will be that subsequent gets perform twice as fast as first get with
     * a >90% success rate.
     */
    @Test
    public void testGetPerformance() {
        Link link = cachedLinkRepository.save(newLink());

        // Perform n trials, each doing to subsequent gets
        int numberOfTrials = 10;
        int numberFailed = 0;
        for (int i = 0; i < numberOfTrials; i++) {
            cache.clear();
            long firstDuration = timedGet(link.getId());
            long secondDuration = timedGet(link.getId());
            if (secondDuration > firstDuration / 2) {
                numberFailed++;
            }
        }

        double acceptableSuccessRate = 0.9;
        double successRate = (numberOfTrials - numberFailed) / numberOfTrials;
        assertThat(successRate, is(greaterThanOrEqualTo(acceptableSuccessRate)));
    }

    private long timedGet(Long id) {
        long firstStartTime = System.nanoTime();
        cachedLinkRepository.findOne(id);
        return System.nanoTime() - firstStartTime;
    }

    private Link newLink() {
        Link link = new Link();
        link.setToken("abcde");
        link.setShortUrl("https://domain.com/abcde");
        link.setFullUrl("https://google.com/?q=hello+world");
        return link;
    }

}
