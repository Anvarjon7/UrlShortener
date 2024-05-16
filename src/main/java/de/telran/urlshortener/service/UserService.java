package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UserRequestDto;
import de.telran.urlshortener.model.entity.user.User;

public interface UserService {

    User registerUser(UserRequestDto userRequestDto);

    User updateUser(Long id, UserRequestDto userRequestDto);

    void deleteUser(Long id);
}
