package demo.link_shortening;

public class ShorteningServiceException extends RuntimeException {

    public ShorteningServiceException(String message) {
        super(message);
    }

    public ShorteningServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
