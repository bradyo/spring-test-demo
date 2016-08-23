package demo.link_shortening.bitly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BitlyShortenResponseData {

    @JsonProperty("url")
    private String url;
}
