package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.model.entity.user.User;

public class UserMapper {
    public UserResponseDto toUserResponseDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .subscriptions(user.getSubscriptions())
                .bindings(user.getBindings())
                .build();
    }
}
