package de.telran.urlshortener.dto;

import de.telran.urlshortener.model.entity.user.User;

import java.time.LocalDate;

/*@NoArgsConstructor
@AllArgsConstructor*/
public record UrlBindingResponseDto(
        Long id,
        String originalUrl,
        String baseUrl,
        String pathPrefix,
        String uid,
        Long count,
        LocalDate creationDate,
        LocalDate expirationDate,
        Boolean isClosed,
        User user) {
}
