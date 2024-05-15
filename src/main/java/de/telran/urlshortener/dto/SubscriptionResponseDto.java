package de.telran.urlshortener.dto;

import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.user.User;

import java.time.LocalDate;


/*@NoArgsConstructor
@AllArgsConstructor*/
public record SubscriptionResponseDto(
        Long id,
        LocalDate creationDate,
        LocalDate expirationDate,
        Status status,
        User user) {
}
