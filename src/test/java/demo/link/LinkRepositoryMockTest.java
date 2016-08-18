package demo.link;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class LinkRepositoryMockTest {

    private EntityManager mockEntityManager;
    private LinkRepository linkRepository;

    @Before
    public void before() {
        mockEntityManager = mock(EntityManager.class);
        linkRepository = new LinkRepository(mockEntityManager);
    }

    @Test
    public void testGet() {
        Link link = stubShortLink();
        when(mockEntityManager.find(Link.class, link.getId()))
            .thenReturn(link);

        Link actualLink = linkRepository.findOne(link.getId());

        assertThat(actualLink, is(link));

        verify(mockEntityManager).find(Link.class, link.getId());
        verifyNoMoreInteractions(mockEntityManager);
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
