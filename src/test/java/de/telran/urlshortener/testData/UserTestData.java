package de.telran.urlshortener.testData;

import de.telran.urlshortener.dto.FullUserResponseDto;
import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.dto.UserResponseDto;
import de.telran.urlshortener.model.entity.user.Role;
import de.telran.urlshortener.model.entity.user.User;

import java.time.LocalDate;
import java.util.Set;

public class UserTestData {
    public static final UserResponseDto USER_RESPONSE_DTO1 = new UserResponseDto(
            1l, "Linda", "Din", "san@example.com"
    );
    public static final UserResponseDto USER_RESPONSE_DTO2 = new UserResponseDto(
            2l, "Artur", "Sinf", "on12@example.com"
    );


    public static final SubscriptionResponseDto SUBSCRIPTION_RESPONSE_DTO1 = new SubscriptionResponseDto(
            1L, LocalDate.now(), LocalDate.now().plusDays(30), null);
    public static final UrlBindingResponseDto URL_BINDING_RESPONSE_DTO1 = new UrlBindingResponseDto(
            1L, "http://example.com", "http://short.url/abc", null, LocalDate.now(), LocalDate.now().plusDays(30));

    public static final FullUserResponseDto FULL_USER_RESPONSE_DTO1 = new FullUserResponseDto(
            USER_RESPONSE_DTO1,
            Set.of(SUBSCRIPTION_RESPONSE_DTO1),
            Set.of(URL_BINDING_RESPONSE_DTO1)
    );
    public static final SubscriptionResponseDto SUBSCRIPTION_RESPONSE_DTO2 = new SubscriptionResponseDto(
            2L, LocalDate.now(), LocalDate.now().plusDays(30), null);
    public static final UrlBindingResponseDto URL_BINDING_RESPONSE_DTO2 = new UrlBindingResponseDto(
            2L, "http://examples.com", "http://short.url/bmw", null, LocalDate.now(), LocalDate.now().plusDays(30));
    public static final FullUserResponseDto FULL_USER_RESPONSE_DTO2 = new FullUserResponseDto(
            USER_RESPONSE_DTO2,
            Set.of(SUBSCRIPTION_RESPONSE_DTO2),
            Set.of(URL_BINDING_RESPONSE_DTO2)
    );
    public static final User TEST_USER1 = User.builder()
            .id(1L)
            .firstName(USER_RESPONSE_DTO1.firstName())
            .lastName(USER_RESPONSE_DTO1.lastName())
            .email(USER_RESPONSE_DTO1.email())
            .password("password123")
            .role(Role.valueOf("ADMIN"))
            .build();
    public static final User TEST_USER2 = User.builder()
            .id(2L)
            .firstName(USER_RESPONSE_DTO2.firstName())
            .lastName(USER_RESPONSE_DTO2.lastName())
            .email(USER_RESPONSE_DTO2.email())
            .password("password555")
            .role(Role.valueOf("USER"))
            .build();
}
