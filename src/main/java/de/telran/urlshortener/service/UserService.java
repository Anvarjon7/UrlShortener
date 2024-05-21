package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.FullUserResponseDto;
import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.model.entity.user.User;

import java.util.List;

public interface UserService {

    User registerUser(UserRequestDto userRequestDto);

    User updateUser(Long id, UserRequestDto userRequestDto);

    List<FullUserResponseDto> getAllUser();

    FullUserResponseDto getByEmail(String email);

    void deleteUser(Long id);
}
