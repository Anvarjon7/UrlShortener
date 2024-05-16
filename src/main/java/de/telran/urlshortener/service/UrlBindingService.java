package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;

import java.util.Set;

public interface UrlBindingService {

    UrlBindingResponseDto createUrlBinding(UrlBindingCreateRequestDto urlBindingCreateRequestDto);

    void closeUrlBinding(Long bindingId);

    UrlBindingResponseDto getByUid(String uid);

    Set<UrlBindingResponseDto> getByUserId(Long userId);

    void deleteUrlBinding(Long bindingId);
}
