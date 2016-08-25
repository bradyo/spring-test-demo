package demo.link_shortening.port_test;

import demo.link_shortening.LinkShorteningService;
import demo.link_shortening.substitutes.NoOpShorteningService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoOpShorteningServicePortTest extends AbstractLinkShorteningServicePortTest {

    @Override
    public LinkShorteningService createService() {
        return new NoOpShorteningService();
    }
}


