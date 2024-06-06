package de.telran.urlshortener.security;

import de.telran.urlshortener.security.model.SignInRequest;
import de.telran.urlshortener.security.model.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse authenticate(SignInRequest request);
}
