package de.telran.urlshortener.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found with such id");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
