package de.telran.urlshortener.exception;

public class UrlBindingNotFoundException extends RuntimeException{

    public UrlBindingNotFoundException(String message) {
        super(message);
    }
}
