package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static de.telran.urlshortener.repository.RepositoryTestData.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SubscriptionRepositoryTest {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        assertTrue(userRepository.findAll().isEmpty());
        subscriptionRepository.deleteAll();
        assertTrue(subscriptionRepository.findAll().isEmpty());
    }

    @Test
    void findAllActual() {
        entityManager.persistAndFlush(USER1);
        entityManager.persistAndFlush(SUBSCRIPTION1);
        Long userId = USER1.getId();
        List<Subscription> actual = subscriptionRepository.findAllActual(userId);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(subscription->
                subscription.getUser().getId().equals(userId)
                        && subscription.getExpirationDate().isAfter(LocalDate.now())
        ));
    }

    @Test
    void findAllValid() {
        entityManager.persistAndFlush(USER2);
        entityManager.persistAndFlush(SUBSCRIPTION2);
        Long userId = USER2.getId();
        List<Subscription> actual = subscriptionRepository.findAllValid(userId);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(subscription->
                subscription.getUser().getId().equals(userId)
                        && subscription.getStatus().equals(Status.PAID)
                        && subscription.getExpirationDate().isAfter(LocalDate.now())
        ));

    }

    @Test
    void findByIdWithUser() {
        entityManager.persistAndFlush(USER3);
        entityManager.persistAndFlush(SUBSCRIPTION3);
        Optional<Subscription> actual = subscriptionRepository.findByIdWithUser(SUBSCRIPTION3.getId());
        assertFalse(actual.isEmpty());
        assertEquals(SUBSCRIPTION3,actual.get());
        assertEquals(USER3,actual.get().getUser());
    }

    @Test
    void findByUserId() {
        entityManager.persistAndFlush(USER4);
        entityManager.persistAndFlush(SUBSCRIPTION4);
        Long userId = USER4.getId();
        Set<Subscription> actual = subscriptionRepository.findByUserId(userId);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(subscription->subscription.getUser().getId().equals(userId)));

    }
}