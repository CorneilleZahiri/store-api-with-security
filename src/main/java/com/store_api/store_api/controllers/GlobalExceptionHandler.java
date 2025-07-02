package com.store_api.store_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

//    // Gérer les erreurs d’unicité (base de données)
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
//        String rootCauseMessage = getRootCauseMessage(ex);
//
//        if (rootCauseMessage != null && rootCauseMessage.toLowerCase().contains("email")) {
//            return ResponseEntity.badRequest().body(
//                    Map.of("email", "Cette adresse email existe déjà.")
//            );
//        }
//
//        return ResponseEntity.badRequest().body(
//                Map.of("error", "Une contrainte d'unicité a été violée.")
//        );
//    }
//
//    // Méthode utilitaire pour obtenir la cause réelle de l’exception
//    private String getRootCauseMessage(Throwable throwable) {
//        Throwable root = throwable;
//        while (root.getCause() != null) {
//            root = root.getCause();
//        }
//        return root.getMessage();
//    }

}
