package demo.link_shortening.bitly;

import lombok.Value;

@Value
public class BitlyShortenResponse {

    @Value
    public class Data {
        private final String shortUrl;
    }

    private final Data data;
    private final String statusCode;
}
