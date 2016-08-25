package demo.link_shortening.substitutes;

import demo.link_shortening.LinkShorteningService;
import demo.link_shortening.ShorteningServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrokenShorteningService implements LinkShorteningService {

    @Override
    public String shorten(String longUrl) {
        throw new ShorteningServiceException("Error occurred");
    }

}
