package de.telran.urlshortener.repositoryTest;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.repository.UrlBindingRepository;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        entityManager.persistAndFlush(TestData.USER1);
        entityManager.persistAndFlush(TestData.URLBINDING1);
        Optional<UrlBinding> actual = urlBindingRepository.findActualByUid(TestData.URLBINDING1.getUid());
        assertTrue(actual.isPresent());
        Assertions.assertEquals(TestData.URLBINDING1, actual.get());
    }

    @Test
    void findByUid() {
        entityManager.persistAndFlush(TestData.USER2);
        entityManager.persistAndFlush(TestData.URLBINDING2);
        Optional<UrlBinding> actual = urlBindingRepository.findByUid(TestData.URLBINDING2.getUid());
        assertTrue(actual.isPresent());
        Assertions.assertEquals(TestData.URLBINDING2, actual.get());
    }

    @Test
    void findByShortUrl() {
        entityManager.persistAndFlush(TestData.USER3);
        entityManager.persistAndFlush(TestData.URLBINDING3);
        Optional<UrlBinding> actual = urlBindingRepository.findByShortUrl(TestData.URLBINDING3.getShort());
        assertTrue(actual.isPresent());
        Assertions.assertEquals(TestData.URLBINDING3, actual.get());
    }

    @Test
    void findByUser_Id() {
        entityManager.persistAndFlush(TestData.USER4);
        entityManager.persistAndFlush(TestData.URLBINDING4);
        Long userId = TestData.USER4.getId();
        Set<UrlBinding> actual = urlBindingRepository.findByUser_Id(userId);
        assertFalse(actual.isEmpty());
        assertTrue(actual.stream().allMatch(urlBinding -> urlBinding.getUser().getId().equals(userId)));
    }
}