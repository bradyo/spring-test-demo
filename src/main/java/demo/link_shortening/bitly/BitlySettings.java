package demo.link_shortening.bitly;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties
public class BitlySettings {

    public String baseUrl;
    public String accessToken;

}
