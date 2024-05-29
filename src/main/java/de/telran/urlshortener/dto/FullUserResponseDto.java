package de.telran.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.subscription.Subscription;

import java.util.Set;

public record FullUserResponseDto(
        UserResponseDto userResponseDto,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Set<Subscription> subscriptions,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Set<UrlBinding> bindings) {
}