package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;
import java.util.Set;

import static de.telran.urlshortener.repository.RepositoryTestData.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UrlBindingRepositoryTest {

    @Autowired
    private UrlBindingRepository urlBindingRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        assertTrue(userRepository.findAll().isEmpty());
        urlBindingRepository.deleteAll();
        assertTrue(urlBindingRepository.findAll().isEmpty());
    }

    @Test
    void findActualByUid() {
        entityManager.persistAndFlush(USER1);
        entityManager.persistAndFlush(URLBINDING1);
        Optional<UrlBinding> actual = urlBindingRepository.findActualByUid(URLBINDING1.getUid());
        assertTrue(actual.isPresent());
        assertEquals(URLBINDING1,actual.get());
    }

    @Test
    void findByUid() {
        entityManager.persistAndFlush(USER2);
        entityManager.persistAndFlush(URLBINDING2);
        Optional<UrlBinding> actual = urlBindingRepository.findByUid(URLBINDING2.getUid());
        assertTrue(actual.isPresent());
        assertEquals(URLBINDING2,actual.get());
    }

    @Test
    void findByShortUrl() {
        entityManager.persistAndFlush(USER3);
        entityManager.persistAndFlush(URLBINDING3);
        Optional<UrlBinding> actual = urlBindingRepository.findByShortUrl(URLBINDING3.getShort());
        assertTrue(actual.isPresent());
        assertEquals(URLBINDING3,actual.get());
    }

    @Test
    void findByUser_Id() {
        entityManager.persistAndFlush(USER4);
        entityManager.persistAndFlush(URLBINDING4);
        Long userId = USER4.getId();
        Set<UrlBinding> actual = urlBindingRepository.findByUser_Id(userId);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(urlBinding->urlBinding.getUser().getId().equals(userId)));
    }
}