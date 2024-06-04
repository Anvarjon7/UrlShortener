package de.telran.urlshortener.repositoryTest;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.repository.UrlBindingRepository;
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
import java.util.Set;

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
        entityManager.persistAndFlush(RepositoryTestData.USER1);
        entityManager.persistAndFlush(RepositoryTestData.URLBINDING1);
        Optional<UrlBinding> actual = urlBindingRepository.findActualByUid(RepositoryTestData.URLBINDING1.getUid());
        assertTrue(actual.isPresent());
        Assertions.assertEquals(RepositoryTestData.URLBINDING1, actual.get());
    }

    @Test
    void findByUid() {
        entityManager.persistAndFlush(RepositoryTestData.USER2);
        entityManager.persistAndFlush(RepositoryTestData.URLBINDING2);
        Optional<UrlBinding> actual = urlBindingRepository.findByUid(RepositoryTestData.URLBINDING2.getUid());
        assertTrue(actual.isPresent());
        Assertions.assertEquals(RepositoryTestData.URLBINDING2, actual.get());
    }

    @Test
    void findByShortUrl() {
        entityManager.persistAndFlush(RepositoryTestData.USER3);
        entityManager.persistAndFlush(RepositoryTestData.URLBINDING3);
        Optional<UrlBinding> actual = urlBindingRepository.findByShortUrl(RepositoryTestData.URLBINDING3.getShort());
        assertTrue(actual.isPresent());
        Assertions.assertEquals(RepositoryTestData.URLBINDING3, actual.get());
    }

    @Test
    void findByUser_Id() {
        entityManager.persistAndFlush(RepositoryTestData.USER4);
        entityManager.persistAndFlush(RepositoryTestData.URLBINDING4);
        Long userId = RepositoryTestData.USER4.getId();
        Set<UrlBinding> actual = urlBindingRepository.findByUser_Id(userId);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(urlBinding -> urlBinding.getUser().getId().equals(userId)));
    }
}