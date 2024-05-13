package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.model.entity.user.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByIdWithSubscriptions(Long userId);

    Optional<User> findByIdWithBindings(Long userId);

    Optional<User> findByIdWithUrlBindingsAndSubscriptions(Long userId);

    User registerUser(UserResponseDto userDto);

    User authenticateUser(String email, String password);

    User updateUser(Long id, UserResponseDto userDto);

    void deleteUser(Long id);
}
