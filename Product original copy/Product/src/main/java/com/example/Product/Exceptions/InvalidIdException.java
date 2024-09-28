package com.example.Product.Exceptions;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String message){
        super(message);
    }
}
