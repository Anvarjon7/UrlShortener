package de.telran.urlshortener.repository;

import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.UserStatisticsResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatisticsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public TopBindingStatisticsResponse getTopBinding (int topParam) {
        entityManager.createQuery(""); //todo написать запрос топ сзвязок по кол-ву переходов
        return null;
    }

}
