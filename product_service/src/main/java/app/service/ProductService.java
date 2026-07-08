package app.service;

import app.entity.Product;
import app.repository.ProductRepository;
import app.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByUserId(Long userId) {
        return productRepository.findByUser_Id(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт с id " + productId + " не найден"));
        return convertToDto(product);
    }

    @Transactional
    public ProductDto updateBalance(Long productId, Double amount) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт с id " + productId + " не найден"));

        if (product.getType().equals("Дебетовая карта") && (product.getBalance() + amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на дебетовой карте");
        }

        product.setBalance(product.getBalance() + amount);
        Product updated = productRepository.save(product);
        return convertToDto(updated);
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getType(),
                product.getUser().getId()
        );
    }
}
