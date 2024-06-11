package com.example.demo.Services;

public class AuthorAlreadyExistsException extends Exception {
    public AuthorAlreadyExistsException(String message) {
        super(message);
    }
}
