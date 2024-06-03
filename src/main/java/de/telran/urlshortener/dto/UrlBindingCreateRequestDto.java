package de.telran.urlshortener.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

public record UrlBindingCreateRequestDto(
        @NotBlank
//        @URL
        String originalUrl,
//        @NotNull @Positive
//        Long userId,
        String pathPrefix,
        @FutureOrPresent
        LocalDate expirationDate
) {
}
