package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.UrlBindingCreateRequestDto;
import de.telran.urlshortener.model.entity.binding.UrlBinding;

import java.util.Set;

public interface UrlBindingService {

    UrlBinding create(UrlBindingCreateRequestDto urlBindingCreateRequestDto);

    void close(Long bindingId);

    UrlBinding getByUid(String uid);

    Set<UrlBinding> getByUserId(Long userId);

    void delete(Long bindingId);

    UrlBinding getByShortUrl(String shortUrl);

    UrlBinding findById(Long Id);

    void incrementClickCount(String uid);
}
