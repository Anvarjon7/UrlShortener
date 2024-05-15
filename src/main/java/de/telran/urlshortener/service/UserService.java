package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UserResponseDto;

public interface UserService {

//    User registerUser(UserDto userDto);
//    User authenticateUser(String email, String password);
//    User updateUser(Long id,UserDto userDto);
//    void deleteUser(Long id);
UserResponseDto registerUser(Long userId);


    UserResponseDto updateUser(Long userId);

    void deleteUser(Long userId);


}
