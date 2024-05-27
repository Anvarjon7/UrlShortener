package de.telran.urlshortener.service.impl;

import de.telran.urlshortener.dto.SubscriptionResponseDto;
import de.telran.urlshortener.mapper.SubscriptionMapper;
import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.SubscriptionRepository;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.userRepository = userRepository;
    }

    public List<Subscription> findByActualSubscriptions(Long userId) {
        return subscriptionRepository.findAllActualSubscriptions(userId);
    }

    public Optional<Subscription> findByIdWithUser(Long id) {
        return subscriptionRepository.findByIdWithUser(id);
    }


    @Override
    public SubscriptionResponseDto create(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")); //todo own Exception
        Subscription subscription = Subscription.builder().build();
        user.addSubscription(subscription);
        subscription = subscriptionRepository.save(subscription);

        return subscriptionMapper.toSubscriptionResponseDto(subscription);
    }


    @Override
    public SubscriptionResponseDto getById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found")); //todo own Exception
        return subscriptionMapper.toSubscriptionResponseDto(subscription);
    }

    @Override
    public SubscriptionResponseDto setPaidStatus(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found")); //todo own Exception
        subscription.setStatus(Status.PAID);
        return subscriptionMapper.toSubscriptionResponseDto(subscriptionRepository.save(subscription));
    }

    @Override
    public void delete(Long id) {
        subscriptionRepository.deleteById(id);
    }


    @Override
    public Set<SubscriptionResponseDto> getByUserId(Long id) {
        Set<Subscription> subscriptions = subscriptionRepository.findByUserId(id);
        return subscriptionMapper.toSubscriptionResponseDtoSet(subscriptions);
    }
}
