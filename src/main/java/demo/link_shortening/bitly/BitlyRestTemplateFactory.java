package demo.link_shortening.bitly;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BitlyRestTemplateFactory {

    private final BitlyObjectMapperFactory bitlyObjectMapperFactory;

    public RestTemplate create() {
        ObjectMapper objectMapper = bitlyObjectMapperFactory.create();

        return new RestTemplate(
            Collections.singletonList(
                new MappingJackson2HttpMessageConverter(objectMapper)
            )
        );
    }
}
