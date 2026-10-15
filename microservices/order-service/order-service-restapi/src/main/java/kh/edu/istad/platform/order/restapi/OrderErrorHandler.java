package kh.edu.istad.platform.order.restapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class OrderErrorHandler {

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<Map<String, Object>> handleDomainRuleViolations(RuntimeException ex) {
        log.warn("Domain rule violation encountered: {}", ex.getMessage());

        Map<String, Object> errorPayload = new HashMap<>();
        errorPayload.put("timestamp", Instant.now().toString());
        errorPayload.put("status", HttpStatus.BAD_REQUEST.value());
        errorPayload.put("error", "Bad Request");
        errorPayload.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorPayload);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationFailures(MethodArgumentNotValidException ex) {
        log.warn("Payload validation failure triggered: {}", ex.getMessage());

        Map<String, Object> errorPayload = new HashMap<>();
        errorPayload.put("timestamp", Instant.now().toString());
        errorPayload.put("status", HttpStatus.BAD_REQUEST.value());
        errorPayload.put("error", "Validation Failed");
        errorPayload.put("message", "The submitted order request contains invalid or missing fields");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorPayload);
    }
}