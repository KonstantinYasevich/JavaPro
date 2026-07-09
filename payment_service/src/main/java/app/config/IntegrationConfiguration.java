package app.config;

import app.dto.PaymentErrorResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class IntegrationConfiguration {

    private final ClientProperties clientProperties;

    public IntegrationConfiguration(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    @Bean
    public RestTemplate restTemplate(ClientResponseErrorHandler clientResponseErrorHandler) {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(clientProperties.getConnectTimeoutMs()))
                .setReadTimeout(Duration.ofMillis(clientProperties.getReadTimeoutMs()))
                .errorHandler(clientResponseErrorHandler)
                .build();
    }
}
