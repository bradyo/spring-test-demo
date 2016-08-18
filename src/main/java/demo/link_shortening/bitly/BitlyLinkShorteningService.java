package demo.link_shortening.bitly;

import demo.link_shortening.LinkShorteningService;
import demo.link_shortening.ShorteningServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * This is a simple service wrapper to the Bitly link shortening API.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BitlyLinkShorteningService implements LinkShorteningService {

    private final RestTemplate restTemplate;
    private final String accessToken;

    public String shorten(String longUrl) {
        String url = UriComponentsBuilder.fromHttpUrl("https://bitly.com/v3/shorten")
            .queryParam("access_token", accessToken)
            .queryParam("longUrl", longUrl)
            .build()
            .toString();
        try {
            return restTemplate.getForObject(url, BitlyShortenResponse.class)
                .getData()
                .getShortUrl();
        }
        catch (RestClientException restClientException) {
            throw new ShorteningServiceException("Failed to shorten url", restClientException);
        }
    }

}
