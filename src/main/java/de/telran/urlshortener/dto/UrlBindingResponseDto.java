package de.telran.urlshortener.dto;

import java.time.LocalDate;

/*@NoArgsConstructor
@AllArgsConstructor*/
public record UrlBindingResponseDto(
        Long id,
        String originalUrl,
        String shortUrl,
        Long count,
        LocalDate creationDate,
        LocalDate expirationDate
) {
}
