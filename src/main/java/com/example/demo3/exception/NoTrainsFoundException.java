package com.example.demo3.exception;

public class NoTrainsFoundException extends RuntimeException {

    public NoTrainsFoundException(String errorMessage) {
        super(errorMessage);
    }
}
