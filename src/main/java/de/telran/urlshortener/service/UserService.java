package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(Long userId);

    UserResponseDto updateUser(Long userId);

    void deleteUser(Long userId);


}
