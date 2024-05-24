package com.example.be3rdproject.cafe.web.error;

public class CafeNotFoundException extends RuntimeException {
    public CafeNotFoundException(String message) {
        super(message);
    }
}
