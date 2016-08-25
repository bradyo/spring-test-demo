package demo.link_shortening.bitly;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class BitlyWireMock {

    private final WireMockServer wireMockServer;
    private final WireMock wireMock;
    private final String baseUrl;

    public BitlyWireMock(int wireMockPort) {
        WireMockConfiguration wireMockConfiguration = WireMockConfiguration.wireMockConfig().port(wireMockPort);
        wireMockServer = new WireMockServer(wireMockConfiguration);
        wireMockServer.start();

        wireMock = new WireMock(wireMockPort);

        baseUrl = "http://localhost:" + wireMockPort;
    }

    public void shutDown() {
        wireMockServer.stop();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void register(MappingBuilder mappingBuilder) {
        wireMock.register(mappingBuilder);
    }
}
