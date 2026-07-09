package app.сontroller;


import app.dto.ItemResponseDto;
import app.dto.ProductDto;
import app.exception.CustomException;
import app.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAllProductsByUserId(@RequestParam("userId") Long userId) {
        return productService.getProductsByUserId(userId);
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable("productId") Long productId) {
        return (app.dto.ProductDto) productService.getProductById(productId);
    }

    @PostMapping("/{productId}/charge")
    public ProductDto chargeProduct(@PathVariable("productId") Long productId, @RequestParam("amount") Double amount) {
        return productService.updateBalance(productId, amount);
    }

}
