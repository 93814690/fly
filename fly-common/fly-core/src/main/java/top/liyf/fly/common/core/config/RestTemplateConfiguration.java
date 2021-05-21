package top.liyf.fly.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import top.liyf.fly.common.core.util.HttpClient;

/**
 * @author liyf
 * Created in 2021-05-19
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(HttpClient.getClient());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }
}
