package demo.link_shortening.bitly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BitlyShortenResponse {

    @JsonProperty("data")
    private BitlyShortenResponseData data;

    @JsonProperty("status_code")
    private String statusCode;
}
