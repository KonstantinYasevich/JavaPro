package app.сontroller;


import app.dto.ProductDto;
import app.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. Запрос всех продуктов по userId
    @GetMapping
    public List<ProductDto> getAllProductsByUserId(@RequestParam("userId") Long userId) {
        return productService.getProductsByUserId(userId);
    }

    // 2. Запрос продукта по его productId
    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable("productId") Long productId) {
        return (app.dto.ProductDto) productService.getProductById(productId);
    }

    // 3. Изменение баланса
    @PostMapping("/{productId}/charge")
    public ProductDto chargeProduct(@PathVariable("productId") Long productId, @RequestParam("amount") Double amount) {
        return productService.updateBalance(productId, amount);
    }
}
