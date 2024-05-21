package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.FullUserResponseDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.model.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public FullUserResponseDto toFullUserResponseDto(User user) {
        return new FullUserResponseDto(
                new UserResponseDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()),
                user.getSubscriptions(),
                user.getBindings()
        );
    }


    public UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail())


                ;
    }

}