package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UserDto;
import de.telran.urlshortener.model.entity.user.User;

public interface UserService {

    User registerUser(UserDto userDto);

    User updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}
