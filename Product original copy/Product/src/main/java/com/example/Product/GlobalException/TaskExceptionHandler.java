package com.example.Product.GlobalException;

import com.example.Product.Exceptions.InvalidIdException;
import com.example.Product.Exceptions.InvalidPriceRange;
import com.example.Product.Exceptions.ProductNameExisted;
import com.example.Product.Exceptions.ProductNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TaskExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(InvalidIdException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidIdException(InvalidIdException ex) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("error", ex.getMessage());
//        return ResponseEntity.badRequest().body(errorResponse);
//    }


        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Map<String, String>> InvalidPriceRange(InvalidPriceRange ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProductNotFound.class)
    public Map<String, String> handleException(ProductNotFound ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProductNameExisted.class)
    public Map<String, String> handleException(ProductNameExisted ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }



}
