package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.exception.SubscriptionNotFoundException;
import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.repository.SubscriptionRepository;
import de.telran.urlshortener.service.SubscriptionService;
import de.telran.urlshortener.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;


    public List<Subscription> findByActual(Long userId) {
        return subscriptionRepository.findAllActual(userId);
    }

    public Optional<Subscription> findByIdWithUser(Long id) {
        return subscriptionRepository.findByIdWithUser(id);
    }


    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new SubscriptionNotFoundException("Not found subscription with " + id));
    }

    @Override
    public Subscription create(Long userId) {
        Subscription subscription = Subscription.builder()
                .user(userService.findById(userId))
                .build();

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Optional<Subscription> getById(Long id) {

        return subscriptionRepository.findById(id);
    }

    @Override
    public Subscription setPaidStatus(Long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        subscription.get().setStatus(Status.PAID);
        return subscriptionRepository.save(subscription.get());

    }

    @Override
    public void delete(Long id) {
        subscriptionRepository.deleteById(id);
    }


    @Override
    public Set<Subscription> getByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId);

    }
}
