package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.SubscriptionRepository;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public List<Subscription> findByActual(Long userId) {
        return subscriptionRepository.findAllActual(userId);
    }

    public Optional<Subscription> findByIdWithUser(Long id) {
        return subscriptionRepository.findByIdWithUser(id);
    }


    @Override
    public Subscription create(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")); //todo own Exception
        Subscription subscription = Subscription.builder()
                .user(user)
                .build();
        subscriptionRepository.save(subscription);

        return subscription;
    }


    @Override
    public Subscription getById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));//todo own Exception
        return subscription;
    }

    @Override
    public Subscription setPaidStatus(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found")); //todo own Exception
        subscription.setStatus(Status.PAID);
        subscriptionRepository.save(subscription);
        return subscription;
    }

    @Override
    public void delete(Long id) {
        subscriptionRepository.deleteById(id);
    }


    @Override
    public Set<Subscription> getByUserId(Long userId) {
        Set<Subscription> byUserId = subscriptionRepository.findByUserId(userId);
        return byUserId;

    }

}
