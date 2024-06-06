package de.telran.urlshortener.repositoryTest;

import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.repository.SubscriptionRepository;
import de.telran.urlshortener.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
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
        entityManager.persistAndFlush(RepositoryTestData.USER1);
        entityManager.persistAndFlush(RepositoryTestData.SUBSCRIPTION1);
        Long userId = RepositoryTestData.USER1.getId();
        List<Subscription> actual = subscriptionRepository.findAllActual(userId);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(subscription ->
                subscription.getUser().getId().equals(userId)
                        && subscription.getExpirationDate().isAfter(LocalDate.now())
        ));
    }

    @Test
    void findAllValid() {
        entityManager.persistAndFlush(RepositoryTestData.USER2);
        entityManager.persistAndFlush(RepositoryTestData.SUBSCRIPTION2);
        Long userId = RepositoryTestData.USER2.getId();
        List<Subscription> actual = subscriptionRepository.findAllValid(userId);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(subscription ->
                subscription.getUser().getId().equals(userId)
                        && subscription.getStatus().equals(Status.PAID)
                        && subscription.getExpirationDate().isAfter(LocalDate.now())
        ));

    }

    @Test
    void findByIdWithUser() {
        entityManager.persistAndFlush(RepositoryTestData.USER3);
        entityManager.persistAndFlush(RepositoryTestData.SUBSCRIPTION3);
        Optional<Subscription> actual = subscriptionRepository.findByIdWithUser(RepositoryTestData.SUBSCRIPTION3.getId());
        assertFalse(actual.isEmpty());
        Assertions.assertEquals(RepositoryTestData.SUBSCRIPTION3, actual.get());
        Assertions.assertEquals(RepositoryTestData.USER3, actual.get().getUser());
    }

    @Test
    void findByUserId() {
        entityManager.persistAndFlush(RepositoryTestData.USER4);
        entityManager.persistAndFlush(RepositoryTestData.SUBSCRIPTION4);
        Long userId = RepositoryTestData.USER4.getId();
        Set<Subscription> actual = subscriptionRepository.findByUserId(userId);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(subscription -> subscription.getUser().getId().equals(userId)));

    }
}