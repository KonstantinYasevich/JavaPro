package app.сontroller;

import app.dto.ItemResponseDto;
import app.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ItemResponseDto> handleException(CustomException exception){
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(
                        new ItemResponseDto(exception.getMessage())
                );
    }
}
