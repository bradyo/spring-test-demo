package demo.link_shortening.bitly;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RetryingBitlyLinkShorteningServiceFactory {

    private final BitlyRestTemplateFactory bitlyRestTemplateFactory;

    public RetryingBitlyLinkShorteningService create(String baseUrl, String accessToken) {
        RestTemplate restTemplate = bitlyRestTemplateFactory.create();

        // Use an exponential backoff policy
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(100);
        backOffPolicy.setMaxInterval(1000);
        backOffPolicy.setMultiplier(1.1);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new AlwaysRetryPolicy());
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return new RetryingBitlyLinkShorteningService(restTemplate, retryTemplate, baseUrl, accessToken);
    }

}
