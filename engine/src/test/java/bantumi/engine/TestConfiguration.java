package bantumi.engine;

import static org.mockito.Mockito.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return mock(RestTemplate.class);
    }
}
