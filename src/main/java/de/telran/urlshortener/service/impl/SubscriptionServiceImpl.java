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
    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found ")); //todo own Exception
    }

    @Override
    public Subscription create(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Subscription subscription = new Subscription();
        subscription.setUser(user.get());
        //.user(user.get())
        //.build();
        return subscriptionRepository.save(subscription);
    }


    @Override
    public Optional<Subscription> getById(Long id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public void setPaidStatus(Long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        subscription.get().setStatus(Status.PAID);
        subscriptionRepository.save(subscription.get());
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
