package demo.link_shortening;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoOpShorteningService implements LinkShorteningService {

    @Override
    public String shorten(String longUrl) {
        return longUrl;
    }

}
