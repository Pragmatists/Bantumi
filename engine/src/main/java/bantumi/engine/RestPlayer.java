package bantumi.engine;

import org.springframework.web.client.RestTemplate;

public class RestPlayer implements Player {
    private Long port;

    private RestTemplate restTemplate;

    public Long getPort() {
        return port;
    }

    String test() {
        return restTemplate.getForObject(String.format("http://localhost:%s/test", getPort()), String.class);
    }

    @Override
    public int selectBucket() {
        return 0;
    }
}
