package de.telran.urlshortener.mapper;

import de.telran.urlshortener.dto.FullUserResponseDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserResponseDto> {

    private final SubscriptionMapper subscriptionMapper;
    private final UrlBindingMapper urlBindingMapper;
    @Override
    public Set<UserResponseDto> toDtoSet(Set<User> users) {
        return Set.of();
    }

    @Override
    public Set<User> toEntitySet(Set<UserResponseDto> userResponseDtos) {
        return Set.of();
    }

    @Override
    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    @Override
    public User toEntity(UserResponseDto userResponseDto) {
        return null;
    }

    public FullUserResponseDto toFullUserResponseDto(User user) {

        return new FullUserResponseDto(
                toDto(user),
                subscriptionMapper.toDtoSet(user.getSubscriptions()),
                urlBindingMapper.toDtoSet(user.getBindings())
        );
    }

}
