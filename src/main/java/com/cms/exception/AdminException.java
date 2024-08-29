package com.cms.exception;

public class AdminException extends Exception {

    // Constructor with only a message
    public AdminException(String message) {
        super(message);
    }

    // Constructor with a message and cause
    public AdminException(String message, Throwable cause) {
        super(message, cause);
    }
}
