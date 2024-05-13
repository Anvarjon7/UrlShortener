package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.SubscriptionResponseDto;

import java.util.Set;

public interface SubscriptionService {
    SubscriptionResponseDto create(Long userId);

    SubscriptionResponseDto getById(Long id);

    SubscriptionResponseDto setPaidStatus(Long id);

    void delete(Long id);

    Set<SubscriptionResponseDto> getByUserId(Long userId);

}
