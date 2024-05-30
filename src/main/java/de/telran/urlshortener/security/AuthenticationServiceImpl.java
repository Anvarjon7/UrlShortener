package de.telran.urlshortener.security;

import de.telran.urlshortener.security.model.JwtAuthenticationResponse;
import de.telran.urlshortener.security.model.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    private JwtService jwtService;
    @Override
    public JwtAuthenticationResponse authenticate(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(),
                        request.getPassword()));
        UserDetails user = userService.loadUserByUsername(request.getLogin());
        String token = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(token);
    }
}
