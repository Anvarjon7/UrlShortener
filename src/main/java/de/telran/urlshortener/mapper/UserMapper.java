package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.FullUserResponseDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.model.entity.user.User;

public class UserMapper {
    public FullUserResponseDto toUserResponseDto(User user) {
        return new FullUserResponseDto(
                new UserResponseDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()),
                user.getSubscriptions(),
                user.getBindings()

        )

                ;
    }
}
