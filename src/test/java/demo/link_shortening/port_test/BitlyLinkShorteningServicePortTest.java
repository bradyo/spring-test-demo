package demo.link_shortening.port_test;

import demo.link_shortening.LinkShorteningService;
import demo.link_shortening.bitly.*;
import org.junit.After;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BitlyLinkShorteningServicePortTest extends AbstractLinkShorteningServicePortTest {

    private static final String testAccessToken = "testAccessToken";

    private final BitlyWireMock bitlyWireMock;

    public BitlyLinkShorteningServicePortTest() {
        bitlyWireMock =  new BitlyWireMock(8888);
    }

    @Override
    public LinkShorteningService createService() {
        bitlyWireMock.register(get(urlPathEqualTo("/v3/shorten"))
                .withQueryParam("access_token", equalTo(testAccessToken))
                .withQueryParam("longUrl", equalTo(inputUrl))
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

        BitlyLinkShorteningServiceFactory factory = new BitlyLinkShorteningServiceFactory(
            new BitlyRestTemplateFactory(
                new BitlyObjectMapperFactory()
            )
        );
        return factory.create(bitlyWireMock.getBaseUrl(), testAccessToken);
    }

    @After
    public void cleanUp() {
        bitlyWireMock.shutDown();
    }
}


