package app.service;

import app.dto.PaymentRequest;
import app.dto.ProductDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class PaymentCoreService {

    private final RestTemplate restTemplate;
    private final String productServiceUrl = "http://localhost:8081/v1/api/products";

    public PaymentCoreService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 1. HTTP-запрос списка продуктов пользователя из внешнего сервиса
    public List<ProductDto> getUserProductsFromProductService(Long userId) {
        String url = UriComponentsBuilder.fromHttpUrl(productServiceUrl)
                .queryParam("userId", userId)
                .toUriString();

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {}
        );
        return response.getBody();
    }

    public String executePayment(PaymentRequest request) {
        List<ProductDto> userProducts = getUserProductsFromProductService(request.userId());
        ProductDto targetProduct = userProducts.stream()
                .filter(p -> p.id().equals(request.productId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Продукт с ID " + request.productId() + " не найден или не принадлежит пользователю " + request.userId()));

        if (request.amount() <= 0) {
            throw new IllegalArgumentException("Сумма платежа должна быть больше нуля");
        }

        if (!targetProduct.type().equals("Кредитный счет") && targetProduct.balance() < request.amount()) {
            throw new IllegalArgumentException("Ошибка платежного ядра: Недостаточно средств. Доступно: " + targetProduct.balance());
        }

        String chargeUrl = UriComponentsBuilder.fromHttpUrl(productServiceUrl + "/" + request.productId() + "/charge")
                .queryParam("amount", -request.amount())
                .toUriString();

        restTemplate.postForEntity(chargeUrl, null, Object.class);

        return "Платеж успешно проведен. Списано " + request.amount() + " со счета №" + targetProduct.accountNumber();
    }
}
