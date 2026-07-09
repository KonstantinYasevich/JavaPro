package app.config;

import app.dto.PaymentErrorResponse;
import app.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class ClientResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public ClientResponseErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        response.getStatusCode();
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()){
            PaymentErrorResponse paymentErrorResponse = objectMapper.readValue(response.getBody(), PaymentErrorResponse.class);
            throw new CustomException(502, paymentErrorResponse.message());
        }
    }
}
