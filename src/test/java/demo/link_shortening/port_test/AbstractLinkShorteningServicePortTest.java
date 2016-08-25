package demo.link_shortening.port_test;

import demo.link_shortening.LinkShorteningService;
import demo.link_shortening.ShorteningServiceException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

abstract public class AbstractLinkShorteningServicePortTest {

    protected static String inputUrl = "http://google.com";

    protected LinkShorteningService linkShorteningService;

    abstract protected LinkShorteningService createService();

    @Before
    public void setUp() {
        linkShorteningService = createService();
    }

    @Test
    public void testCreateLink() {
        String shortened = linkShorteningService.shorten(inputUrl);
        assertThat(shortened, not(isEmptyOrNullString()));
    }

    @Test(expected = ShorteningServiceException.class)
    public void testShortenEmptyString() {
        linkShorteningService.shorten("");
    }
}


