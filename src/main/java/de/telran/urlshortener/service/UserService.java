package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.model.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponseDto registerUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUser();

    UserResponseDto getById(Long id);

    UserResponseDto getByEmail(String email);

    void deleteUser(Long id);

    Optional<User> findByIdWithBindings(Long id);

    Optional<User> findByIdWithUrlBindingsAndSubscriptions(Long id);

    Optional<User> findByIdWithSubscriptions(Long id);

    User findById(Long id);
}
