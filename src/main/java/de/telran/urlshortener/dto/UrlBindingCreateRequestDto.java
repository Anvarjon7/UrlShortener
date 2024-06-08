package de.telran.urlshortener.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;


public record UrlBindingCreateRequestDto(
        @NotBlank
        String originalUrl,
        String pathPrefix,
        @FutureOrPresent
        LocalDate expirationDate
) {
}
