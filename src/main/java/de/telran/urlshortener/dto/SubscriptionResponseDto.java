package de.telran.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.telran.urlshortener.model.entity.subscription.Status;

import java.time.LocalDate;


public record SubscriptionResponseDto(
        Long id,
        LocalDate creationDate,
        LocalDate expirationDate,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Status status
) {
}
