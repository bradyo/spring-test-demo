package demo.link_shortening.bitly;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import demo.link_shortening.ShorteningServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class BitlyLinkShorteningServiceTest {

    private static final String testAccessToken = "testToken";

    private WireMockServer bitlyWireMockServer;
    private WireMock bitlyWireMock;
    private BitlyLinkShorteningService bitlyLinkShorteningService;

    @Before
    public void beforeTest() {
        Integer wireMockPort = 8888;
        WireMockConfiguration wireMockConfiguration = WireMockConfiguration.wireMockConfig().port(wireMockPort);
        bitlyWireMockServer = new WireMockServer(wireMockConfiguration);
        bitlyWireMockServer.start();
        bitlyWireMock = new WireMock(wireMockPort);

        BitlyLinkShorteningServiceFactory factory = new BitlyLinkShorteningServiceFactory(
            new BitlyRestTemplateFactory(
                new BitlyObjectMapperFactory()
            )
        );
        String baseUrl = "http://localhost:" + wireMockPort;
        bitlyLinkShorteningService = factory.create(baseUrl, testAccessToken);
    }

    @After
    public void after() {
        bitlyWireMockServer.stop();
    }

    @Test
    public void testShorten() {
        String url = "http://google.com";
        bitlyWireMock.register(get(urlPathEqualTo("/v3/shorten"))
            .withQueryParam("access_token", equalTo(testAccessToken))
            .withQueryParam("longUrl", equalTo(url))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\n" +
                        "  \"data\": {\n" +
                        "    \"global_hash\": \"900913\", \n" +
                        "    \"hash\": \"ze6poY\", \n" +
                        "    \"long_url\": \"http://google.com/\", \n" +
                        "    \"new_hash\": 0, \n" +
                        "    \"url\": \"http://bit.ly/ze6poY\"\n" +
                        "  }, \n" +
                        "  \"status_code\": 200, \n" +
                        "  \"status_txt\": \"OK\"\n" +
                        "}")
            )
        );

        String shortUrl = bitlyLinkShorteningService.shorten(url);

        assertThat(shortUrl, is("http://bit.ly/ze6poY"));
    }

    @Test
    public void testShortenError() {
        String url = "http://google.com";
        bitlyWireMock.register(get(urlPathEqualTo("/v3/shorten"))
            .withQueryParam("access_token", equalTo(testAccessToken))
            .withQueryParam("longUrl", equalTo(url))
            .willReturn(
                aResponse()
                    .withStatus(500)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\n" +
                        "  \"status_code\": 500,\n" +
                        "  \"status_txt\": \"UNKNOWN ERROR\",\n" +
                        "  \"data\" : null\n" +
                        "}")
            )
        );

        try {
            String shortUrl = bitlyLinkShorteningService.shorten(url);
            fail("expected exception but got url \"" + shortUrl + "\"");
        } catch (ShorteningServiceException e) {
            assertThat(e.getMessage(), is("Failed to shorten url"));
        }
    }

    @Test
    public void testBadAuthToken() {
        String badAccessToken = "bad";
        String url = "http://google.com";
        bitlyWireMock.register(get(urlPathEqualTo("/v3/shorten"))
            .withQueryParam("access_token", equalTo(badAccessToken))
            .withQueryParam("longUrl", equalTo(url))
            .willReturn(
                aResponse()
                    .withStatus(400)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\n" +
                        "  \"status_code\": 400,\n" +
                        "  \"status_txt\": \"MISSING_ARG_LOGIN\",\n" +
                        "  \"data\" : null\n" +
                        "}")
            )
        );

        try {
            String shortUrl = bitlyLinkShorteningService.shorten(url);
            fail("expected exception but got url \"" + shortUrl + "\"");
        } catch (ShorteningServiceException e) {
            assertThat(e.getMessage(), is("Failed to shorten url"));
        }
    }
}
