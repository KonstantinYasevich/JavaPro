package java_pro.ontroller;

import java_pro.dto.ProductDto;
import org.springframework.web.bind.annotation.*;
import java_pro.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
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
        return productService.getProductById(productId);
    }
}
