package de.telran.urlshortener.repositoryTest;

import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UserRepository;
import de.telran.urlshortener.testData.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    void findByIdWithSubscriptions() {
        entityManager.persistAndFlush(TestData.USER1);
        Optional<User> actualUser = userRepository.findByIdWithSubscriptions(TestData.USER1.getId());
        assertTrue(actualUser.isPresent());
        assertEquals(TestData.USER1, actualUser.get());
    }

    @Test
    void findByIdWithBindings() {
        entityManager.persistAndFlush(TestData.USER2);
        Optional<User> actualUser = userRepository.findByIdWithBindings(TestData.USER2.getId());
        assertTrue(actualUser.isPresent());
        assertEquals(TestData.USER2, actualUser.get());
    }

    @Test
    void findByIdWithUrlBindingsAndSubscriptions() {
        entityManager.persistAndFlush(TestData.USER3);
        entityManager.persistAndFlush(TestData.USER3);
        entityManager.persistAndFlush(TestData.USER3);
        Optional<User> actualUser = userRepository.findByIdWithUrlBindingsAndSubscriptions(TestData.USER3.getId());
        assertTrue(actualUser.isPresent());
        Assertions.assertEquals(TestData.USER3, actualUser.get());
    }

    @Test
    void findByEmail() {
        entityManager.persistAndFlush(TestData.USER4);
        Optional<User> actualUser = userRepository.findByEmail(TestData.USER4.getEmail());
        assertTrue(actualUser.isPresent());
        Assertions.assertEquals(TestData.USER4, actualUser.get());
    }
}