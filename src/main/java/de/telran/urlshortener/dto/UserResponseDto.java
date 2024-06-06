package de.telran.urlshortener.dto;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.subscription.Subscription;

import java.util.Set;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {

}
