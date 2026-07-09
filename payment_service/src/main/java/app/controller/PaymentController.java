package app.controller;

import app.dto.ItemResponseDto;
import app.dto.PaymentRequest;
import app.dto.ProductDto;
import app.exception.CustomException;
import app.service.PaymentCoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/payments")
public class PaymentController {

    private final PaymentCoreService paymentCoreService;

    public PaymentController(PaymentCoreService paymentCoreService) {
        this.paymentCoreService = paymentCoreService;
    }

    @GetMapping("/user-products/{userId}")
    public List<ProductDto> getProducts(@PathVariable("userId") Long userId) {
        return paymentCoreService.getUserProductsFromProductService(userId);
    }

    @PostMapping("/execute")
    public String pay(@RequestBody PaymentRequest request) {
        return paymentCoreService.executePayment(request);
    }
}
