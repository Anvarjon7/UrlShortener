package de.telran.urlshortener.dto;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.subscription.Subscription;

import java.util.Set;

public record FullUserResponseDto(
        UserResponseDto userResponseDto,
        Set<Subscription> subscriptions,
        Set<UrlBinding> bindings) {

}
