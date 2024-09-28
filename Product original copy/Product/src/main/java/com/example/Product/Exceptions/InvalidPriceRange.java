package com.example.Product.Exceptions;

public class InvalidPriceRange extends RuntimeException {
    public InvalidPriceRange(String message) {
        super(message);
    }
}
