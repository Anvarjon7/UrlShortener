package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UrlBindingDto;
import de.telran.urlshortener.dto.UrlBindingResponseDto;
import de.telran.urlshortener.model.entity.binding.UrlBinding;

public interface UrlBindingService {
    UrlBinding createUrlBinding(UrlBindingDto urlBindingDto);
    void deleteUrlBinding(Long bindingId);

}
