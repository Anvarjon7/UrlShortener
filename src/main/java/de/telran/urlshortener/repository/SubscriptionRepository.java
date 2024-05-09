package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.expirationDate >= CURRENT_DATE")
    List<Subscription> findAllActualSubscriptions();
}
