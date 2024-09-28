package com.example.Product.Exceptions;

public class ProductNameExisted extends RuntimeException {
    public ProductNameExisted(String message){
        super(message);
    }
}
