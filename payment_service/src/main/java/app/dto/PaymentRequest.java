package app.dto;

public record PaymentRequest(
        Long userId,
        Long productId,
        Double amount,
        String description
) {}