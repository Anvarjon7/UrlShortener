package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.repository.SubscriptionRepository;
import de.telran.urlshortener.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    @Override
    public SubscriptionResponseDto create(Long userId) {
        return null;
    }

    @Override
    public SubscriptionResponseDto getById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found")); //todo own Exception
        return null;
    }

    @Override
    public SubscriptionResponseDto setPaidStatus(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Set<SubscriptionResponseDto> getByUserId(Long userId) {
        return null;
    }
}
