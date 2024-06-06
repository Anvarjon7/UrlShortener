package de.telran.urlshortener.repositoryTest;

import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.TopRecord;
import de.telran.urlshortener.repository.StatisticsRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.telran.urlshortener.repositoryTest.RepositoryTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({StatisticsRepository.class})
@ActiveProfiles("test")
@Transactional
class StatisticsRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private StatisticsRepository statisticsRepository;

    @BeforeEach
    void setUp() {

        entityManager.persist(USER1);
        entityManager.persist(USER2);
        entityManager.persist(USER3);
        entityManager.persist(USER4);

        URLBINDING1.setCount(10L);
        URLBINDING2.setCount(20L);
        URLBINDING3.setCount(30L);
        URLBINDING4.setCount(40L);

        entityManager.persist(URLBINDING1);
        entityManager.persist(URLBINDING2);
        entityManager.persist(URLBINDING3);
        entityManager.persist(URLBINDING4);

        entityManager.flush();
    }

    @Test
    void getTopUrlBinding() {

        TopBindingStatisticsResponse response = statisticsRepository.getTopUrlBinding(3);

        List<TopRecord> topRecords = response.top();
        assertEquals(3, topRecords.size());

        assertEquals(URLBINDING4.getId(), topRecords.get(0).bindingId());
        assertEquals(USER4.getId(), topRecords.get(0).userId());
        assertEquals(40, topRecords.get(0).count());

        assertEquals(URLBINDING3.getId(), topRecords.get(1).bindingId());
        assertEquals(USER3.getId(), topRecords.get(1).userId());
        assertEquals(30, topRecords.get(1).count());

        assertEquals(URLBINDING2.getId(), topRecords.get(2).bindingId());
        assertEquals(USER2.getId(), topRecords.get(2).userId());
        assertEquals(20, topRecords.get(2).count());
    }
}