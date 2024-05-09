package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.repository.SubscriptionRepository;
import de.telran.urlshortener.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<SubscriptionRepository> findAllActualSubscriptions() {
        return subscriptionRepository.findAllActualSubscriptions();
    }
}
