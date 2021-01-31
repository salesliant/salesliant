package com.salesliant.validator;

public class InvalidException extends Exception {

    public InvalidException() {
    }

    public InvalidException(String message) {
        super(message);
    }
}
