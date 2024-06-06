package de.telran.urlshortener.exception;

public class UserNotAuthenticatedException extends RuntimeException{

    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
