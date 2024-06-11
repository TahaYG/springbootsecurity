package com.example.demo.Services;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
