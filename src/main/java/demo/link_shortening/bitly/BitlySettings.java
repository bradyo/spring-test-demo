package demo.link_shortening.bitly;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Value
@Component
@ConfigurationProperties
public class BitlySettings {

    public final String baseUrl;

    public final String accessToken;

}
