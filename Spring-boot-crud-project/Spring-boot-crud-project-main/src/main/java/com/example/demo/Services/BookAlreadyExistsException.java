package com.example.demo.Services;

public class BookAlreadyExistsException extends Exception {
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}

