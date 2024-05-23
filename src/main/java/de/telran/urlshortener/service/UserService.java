package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto registerUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUser();

    UserResponseDto getById(Long id);

    UserResponseDto getByEmail(String email);

    void deleteUser(Long id);
}
