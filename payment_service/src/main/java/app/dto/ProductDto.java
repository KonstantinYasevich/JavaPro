package app.dto;

public record ProductDto(
        Long id,
        Integer accountNumber,
        Double balance,
        String type,
        Long userId
) {}