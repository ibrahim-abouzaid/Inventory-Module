package com.example.inventorymodule.Inventory.Controller;

import com.example.inventorymodule.Inventory.exceptions.CrossTenantAccessException;
import com.example.inventorymodule.Inventory.exceptions.MissingTenantException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MissingTenantException.class)
    public ResponseEntity<String> handleMissingTenant(MissingTenantException ex) {
        // Missing X-Tenant-Id -> 400 Bad Request
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CrossTenantAccessException.class)
    public ResponseEntity<String> handleCrossTenantAccess(CrossTenantAccessException ex) {
        // Cross-tenant access blocked -> 403 Forbidden
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        Class<?> requiredType = ex.getRequiredType();

        // 1. Check if the error is specifically because of an Enum mismatch
        if (requiredType != null && requiredType.isEnum()) {
            String acceptedValues = Arrays.toString(requiredType.getEnumConstants());
            String errorMessage = String.format("Invalid value '%s' for parameter '%s'. Accepted values are: %s",
                    ex.getValue(), paramName, acceptedValues);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        // 2. Generic fallback for other type mismatches (e.g., if they pass "abc" for a UUID)
        String fallbackMessage = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
                ex.getValue(), paramName, requiredType.getSimpleName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fallbackMessage);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGlobalException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
