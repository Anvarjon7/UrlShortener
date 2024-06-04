package de.telran.urlshortener.repositoryTest;

import de.telran.urlshortener.model.entity.user.User;
import de.telran.urlshortener.repository.UserRepository;
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
        entityManager.persistAndFlush(RepositoryTestData.USER1);
        Optional<User> actualUser = userRepository.findByIdWithSubscriptions(RepositoryTestData.USER1.getId());
        assertTrue(actualUser.isPresent());
        Assertions.assertEquals(RepositoryTestData.USER1, actualUser.get());
    }

    @Test
    void findByIdWithBindings() {
        entityManager.persistAndFlush(RepositoryTestData.USER2);
        Optional<User> actualUser = userRepository.findByIdWithBindings(RepositoryTestData.USER2.getId());
        assertTrue(actualUser.isPresent());
        Assertions.assertEquals(RepositoryTestData.USER2, actualUser.get());
    }

    @Test
    void findByIdWithUrlBindingsAndSubscriptions() {
        entityManager.persistAndFlush(RepositoryTestData.USER3);
        entityManager.persistAndFlush(RepositoryTestData.USER3);
        entityManager.persistAndFlush(RepositoryTestData.USER3);
        Optional<User> actualUser = userRepository.findByIdWithUrlBindingsAndSubscriptions(RepositoryTestData.USER3.getId());
        assertTrue(actualUser.isPresent());
        Assertions.assertEquals(RepositoryTestData.USER3, actualUser.get());
    }

    @Test
    void findByEmail() {
        entityManager.persistAndFlush(RepositoryTestData.USER4);
        Optional<User> actualUser = userRepository.findByEmail(RepositoryTestData.USER4.getEmail());
        assertTrue(actualUser.isPresent());
        Assertions.assertEquals(RepositoryTestData.USER4, actualUser.get());
    }
}