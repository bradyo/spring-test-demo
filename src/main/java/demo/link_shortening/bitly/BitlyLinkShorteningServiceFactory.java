package demo.link_shortening.bitly;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BitlyLinkShorteningServiceFactory {

    private final BitlyRestTemplateFactory bitlyRestTemplateFactory;

    public BitlyLinkShorteningService create(String baseUrl, String accessToken) {
        RestTemplate restTemplate = bitlyRestTemplateFactory.create();

        return new BitlyLinkShorteningService(restTemplate, baseUrl, accessToken);
    }

}
