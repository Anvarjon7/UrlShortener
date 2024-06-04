package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static de.telran.urlshortener.repository.RepositoryTestData.*;
import static org.junit.jupiter.api.Assertions.*;

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
        entityManager.persistAndFlush(USER1);
        Optional<User> actualUser = userRepository.findByIdWithSubscriptions(USER1.getId());
        assertTrue(actualUser.isPresent());
        assertEquals(USER1,actualUser.get());
    }

    @Test
    void findByIdWithBindings() {
        entityManager.persistAndFlush(USER2);
        Optional<User> actualUser = userRepository.findByIdWithBindings(USER2.getId());
        assertTrue(actualUser.isPresent());
        assertEquals(USER2,actualUser.get());
    }

    @Test
    void findByIdWithUrlBindingsAndSubscriptions() {
        entityManager.persistAndFlush(USER3);
        entityManager.persistAndFlush(USER3); entityManager.persistAndFlush(USER3);
        Optional<User> actualUser = userRepository.findByIdWithUrlBindingsAndSubscriptions(USER3.getId());
        assertTrue(actualUser.isPresent());
        assertEquals(USER3,actualUser.get());
    }

    @Test
    void findByEmail() {
        entityManager.persistAndFlush(USER4);
        Optional<User> actualUser = userRepository.findByEmail(USER4.getEmail());
        assertTrue(actualUser.isPresent());
        assertEquals(USER4,actualUser.get());
    }
}