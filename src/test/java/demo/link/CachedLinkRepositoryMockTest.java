package demo.link;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.Cache;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class CachedLinkRepositoryMockTest {

    private EntityManager mockEntityManager;
    private Cache mockCache;
    private CachedLinkRepository cachedLinkRepository;

    @Before
    public void before() {
        mockEntityManager = mock(EntityManager.class);
        mockCache = mock(Cache.class);
        cachedLinkRepository = new CachedLinkRepository(mockEntityManager, mockCache);
    }

    @Test
    public void testGetCacheHit() {
        Long id = 1L;
        Link link = stubShortLink();
        when(mockCache.get(id, Link.class))
            .thenReturn(link);

        Link actualLink = cachedLinkRepository.findOne(1L);

        assertThat(actualLink, is(link));

        verify(mockCache).get(id, Link.class);
        verifyNoMoreInteractions(mockCache, mockEntityManager);
    }

    @Test
    public void testGetNoCacheHit() {
        Long id = 1L;
        Link link = stubShortLink();
        when(mockCache.get(id, Link.class))
            .thenReturn(null);
        when(mockEntityManager.find(Link.class, id))
            .thenReturn(link);

        Link actualLink = cachedLinkRepository.findOne(id);

        assertThat(actualLink, is(link));

        verify(mockCache).get(id, Link.class);
        verify(mockEntityManager).find(Link.class, id);
        verify(mockCache).put(id, link);
        verifyNoMoreInteractions(mockCache, mockEntityManager);
    }

    private Link stubShortLink() {
        Link link = new Link();
        link.setId(1L);
        link.setToken("abcde");
        link.setShortUrl("https://domain.com/abcde");
        link.setFullUrl("https://google.com/?q=hello+world");
        return link;
    }
}
