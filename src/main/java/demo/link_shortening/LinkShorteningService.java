package demo.link_shortening;

public interface LinkShorteningService {

    /**
     *
     * @param longUrl URL to shorten
     * @return the shortened URL
     *
     * @throws ShorteningServiceException
     * @throws
     */
    String shorten(String longUrl);

}
