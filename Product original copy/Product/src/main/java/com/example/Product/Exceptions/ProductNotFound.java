package com.example.Product.Exceptions;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound(String message){
        super(message);
    }
}
