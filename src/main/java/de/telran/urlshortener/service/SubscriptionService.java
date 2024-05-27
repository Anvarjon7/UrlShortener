package de.telran.urlshortener.service;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.model.entity.subscription.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubscriptionService {


    SubscriptionResponseDto create(Long userId);

    SubscriptionResponseDto getById(Long id);

    SubscriptionResponseDto setPaidStatus(Long id);

    void delete(Long id);

    Set<SubscriptionResponseDto> getByUserId(Long userId);

    Optional<Subscription> findByIdWithUser(Long id);

    List<Subscription> findByActualSubscriptions(Long userId);



}
