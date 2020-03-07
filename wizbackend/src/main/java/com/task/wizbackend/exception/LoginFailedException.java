package com.task.wizbackend.exception;

public class LoginFailedException extends Exception {

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
