package com.iot.shared;

public class EmailExistsException extends Exception {
    public EmailExistsException(String message) {
        super(message);
    }
}
