package de.telran.urlshortener.service;

import de.telran.urlshortener.model.entity.subscription.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubscriptionService {

    Subscription findById(Long id);

    Subscription create(Long userId);

    Optional<Subscription> getById(Long id);

    void setPaidStatus(Long id);

    void delete(Long id);

    Set<Subscription> getByUserId(Long userId);

    Optional<Subscription> findByIdWithUser(Long id);

    List<Subscription> findByActual(Long userId);


}
